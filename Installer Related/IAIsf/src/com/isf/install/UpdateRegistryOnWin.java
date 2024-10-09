package com.isf.install;

import com.isf.install.utils.ArchiveResourceFileReader;
import com.isf.install.utils.DataInputLogReader;
import com.zerog.ia.api.pub.*;
import com.isf.install.utils.SimpleRegistryManagerPlus;
import java.util.*;
import java.net.*;
import java.io.*;

/**
 * UpdateRegistryOnWin.java
 *
 * This class is used to install and uninstall a series of Win32 Registry
 * entries.
 *
 */
public class UpdateRegistryOnWin extends CustomCodeAction
{
	// the name of the class -- primarily used by Msg methods.
	private static final String CLASS_NAME = "UpdateRegistryOnWin";
	// the status message to be displayed during installation.
	private static final String INSTALL_MESSAGE = "Registry Entries";
	// the status message to be displayed during uninstall.
	private static final String UNINSTALL_MESSAGE = "Registry Entries";

	// the default directory where resources for this class are located.
	private String resourceDirectory = "com/isf/install/utils/resources/";
	// the default resource file used by this class.
	private String resourceFile = "registry.txt";
	// the default delimiter used when parsing a resource file.
	protected String resourceDelimiter = ",";

	// used to terminate and recogize the termination of DataInput/DataOutput streams.
	protected static final String dataTerminator = "-- END OF LINE --";

	// used to determine if the SRMP rollback functionality should be on or off.
	protected boolean rollback = true;

	// determines if debugMsg will produce output during execution.
	public  static boolean debug = false;


	// ***************************************************************************
	// Debugging Methods
	// ***************************************************************************

	/**
	 *
	 *	This method outputs the additional debugging messages from this
	 *  custom code action.   The messages are directed to 'stderr' and will
	 *  only be output if the class's 'debug' flag has been turned on and
	 *  the installer is run in 'debug' mode.
	 *
	 */
	protected static void debugMsg(String msg)
	{
		if (debug)	// if output should be shown.
		{
			System.err.println(CLASS_NAME + "(D): " + msg);	// show message.
		}
	}


	/**
	 *
	 *	This method outputs the standard messages from this custom code action
	 *  which are always seen in 'stdout' when an installer is
	 *  run in 'debug' mode.
	 *
	 */
	protected static void stdMsg(String msg)
	{
		// stdMsgs are always output.
		System.out.println(CLASS_NAME + ": " + msg);	// show message.
	}

	// ***************************************************************************

	/**
	 *
	 *	This method changes the default resource directory in which
	 *  this class expects to find the resources it will use.
	 *
	 */
	public void setResourceDirectory(String resourceDirectory)
	{
		this.resourceDirectory = resourceDirectory;
	}


	/**
	 *
	 *	This method changes the default resource file used by this class.
	 *
	 */
	public void setResourceFile(String resourceFile)
	{
		this.resourceFile = resourceFile;
	}


	/**
	 *
	 *  This is the method that is called at install-time.  The InstallerProxy
	 *  instance provides methods to access information in the installer,
	 *  set status, and control flow.
	 *
	 */
	public void install( InstallerProxy ip ) throws InstallException
	{
		SimpleRegistryManagerPlus srmp;
		// will hold the registry entries to be made.
		String [] registryEntries;
		// will hold the first index into the registryEntries array that
		// contains an actual registry entry.
		int startOfRegistryEntries;

		//
		// check to see if we should be in 'full' debugging mode.
		//
		String checkDebug = null;
		if (debug == true
			|| (((checkDebug = (String)ip.getVariable("CC_DEBUG")) != null)
			      && checkDebug.equalsIgnoreCase("TRUE"))
			|| (((checkDebug = (String)ip.getVariable("lax.nl.env.CC_DEBUG")) != null)
			      && checkDebug.equalsIgnoreCase("TRUE"))
			|| (((checkDebug = (String)System.getProperty("cc_debug")) != null)
			      && checkDebug.equalsIgnoreCase("TRUE")))
		{
			debug = true;
			SimpleRegistryManagerPlus.debug = true;
		}

		//
		// Read in the registry entries from the resource file.
		//
		try
		{
			registryEntries = ArchiveResourceFileReader.readContents(ip, resourceDirectory + resourceFile);
		}
		catch (IOException ioe)
		{
			stdMsg("Error reading resource file.");
			throw new NonfatalInstallException (ioe.getMessage());
		}

		//
		// Check for Delimiter and Rollback directives, and if found adjust the
		// value of 'startOfRegistryEntries' to reflect the first index into
		// the array that contains an actual registry entry.  If developer
		// defined directives are not found the defaults for this class
		// will be used.
		//
		startOfRegistryEntries = checkForSpecialDirectives(registryEntries, 0);
		debugMsg("startOfRegistryEntries = " + startOfRegistryEntries);

		//
		// set 'srmp' based on delimiter.
		//
		srmp = new SimpleRegistryManagerPlus(ip, resourceDelimiter);

		//
		// set the state of the rollback functionality. Rollback is on
		// by default.
		//
		if (!rollback)
		{
			srmp.disableRollback();
		}

		//
		// this section of code is responsible for making the registry entries and
		// recording the deletion data for retrieval during uninstall.
		//
		try
		{
			// will hold the data that will be used to removed the entries at uninstall.
			String deletionData;

			DataOutput dout = ip.getLogOutput();

			//
			// Save the delimiter used for reference during uninstall
			//
			dout.writeUTF("DELIMITER=" + resourceDelimiter);

			for (int i = startOfRegistryEntries; i < registryEntries.length; i++)
			{
				//
				// attempt to make the registry entry and receive the deletion data
				//
				deletionData = srmp.setRegistryEntryDelimitedString(registryEntries[i]);

				//
				// if: setReg was successful (i.e., deletionData != null)
				//
				if (deletionData != null)
				{
					//
					// if: empty string, we are not to delete (i.e, do not bother to record the
					//     deletion data.
					//
					if (!deletionData.equals(""))
					{
						dout.writeUTF(deletionData);
					}
				}
				else
				{
					stdMsg("Error making RegEntry: " + registryEntries[i]);
				}
			}

			dout.writeUTF(dataTerminator); // signify the end of the date entries.
		}
		catch ( IOException ioe )
		{
			//
			// An error has occurred attempting to save the uninstall data.  This
			// exception will be noted in the debug output and a NonfatalInstallException
			// will be propegated to a higher level. If the developer determines this
			// error should be dealt in an additional manner, the exception should be
			// dealt with here.
			//
			stdMsg("Error saving registry info for use during uninstall.");
			throw new NonfatalInstallException( ioe.getMessage() );
		}

	}


	/**
	 *
	 * This private method determines if a delimiter or rolback directive was included
	 * in the resource file for this class.  U
	 *
	 */
	private int checkForSpecialDirectives(String [] resourceFileContents, int index)
	{
		if (index < resourceFileContents.length)
		{
			StringTokenizer st = new StringTokenizer(resourceFileContents[index], "=");

			if (st.countTokens() > 1)
			{
				String firstToken = st.nextToken().trim();

				if (firstToken.equalsIgnoreCase("DELIMITER"))
				{
					debugMsg("DELIMITER found. index = " + index);

					resourceDelimiter = st.nextToken().trim();
					index = checkForSpecialDirectives(resourceFileContents, index + 1);
				}
				else if (firstToken.equalsIgnoreCase("ROLLBACK"))
				{
					debugMsg("ROLLBACK found. index = " + index);

					if (st.nextToken().trim().equalsIgnoreCase("OFF"))
					{
						rollback = false;
					}

					index = checkForSpecialDirectives(resourceFileContents, index + 1);
				}
			}
		}

		debugMsg("returning index = " + index);
		return index;
	}


	/**
	 *
	 *  This is the method that is called at uninstall-time.  The DataInput
	 *  instance provides access to any information written at install-time
	 *  with the instance of DataOutput provided by UninstallerProxy.getLogOutput().
	 *
	 */
	public void uninstall( UninstallerProxy up ) throws InstallException
	{
		SimpleRegistryManagerPlus srmp;
		DataInput din;
		String deletionData = null;
		String[] deletionDataEntries;
		int startOfDeletionData;

		//
		// check to see if we should be in 'full' debugging mode.
		//
		String checkDebug = null;
		if (debug == true
			|| ((checkDebug = (String)System.getProperty("cc_debug")) != null)
			     && checkDebug.equalsIgnoreCase("TRUE"))
		{
			debug = true;
			SimpleRegistryManagerPlus.debug = true;
		}

		try
		{
			deletionDataEntries = DataInputLogReader.readContents(up, dataTerminator);
		}
		catch (IOException ioe)
		{
			stdMsg("Error reading deletion data log file.");
			throw new NonfatalInstallException (ioe.getMessage());
		}

		startOfDeletionData = checkForSpecialDirectives(deletionDataEntries, 0);
		debugMsg("startOfDeletionData = " + startOfDeletionData);

		//
		// set 'srmp' based on delimiter.
		//
		srmp = new SimpleRegistryManagerPlus(up, resourceDelimiter);

		//
		// This section od code handles the actual removal/rollback of the installed
		// registry entries.
		//
		boolean success;

		//
		// remove the entries in reverse order that they were made.
		//
		for(int i = deletionDataEntries.length; i > startOfDeletionData; i--)
		{
			//
			// i - 1, because index starts at zero.
			//
			deletionData = deletionDataEntries[i - 1];

			success = srmp.deleteRegistryEntryDelimitedString(deletionData);

			if(success)
			{
				debugMsg(deletionData + " removed.");
			}
			else
			{
				stdMsg("Unable to remove registry entry: " + deletionData);
			}
		}

	}


	/**
	 *
	 *  This method will be called to display a status message during the
	 *  installation.
	 *
	 */
	public String getInstallStatusMessage()
	{
		return INSTALL_MESSAGE;	// screen will say "Installing... <INSTALL_MESSAGE>"
	}


	/**
	 *
	 *  This method will be called to display a status message during the
	 *  uninstall.
	 *
	 */
	public String getUninstallStatusMessage()
	{
		return UNINSTALL_MESSAGE;	// screen will say "Uninstalling... <UNINSTALL_MESSAGE>"
	}

	// ***************************************************************************
}
