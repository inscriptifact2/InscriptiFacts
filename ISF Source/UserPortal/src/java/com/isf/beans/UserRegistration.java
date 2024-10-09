/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.beans;

import com.isf.dao.UserDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author raok1
 */
public class UserRegistration {

    private String action;
    private String lastName;
    private String firstName;
    private String middleName;
    private String salutation;
    private String nameSuffix;
    private String primaryEmail;
    private String altEmail1;
    private String altEmail2;
    private String altEmail3;
    private String altEmail4;
    private String primaryMobile;
    private String altMobile;
    private String userCity;
    private String altCity;
    private String userCountry;
    private String altCountry;
    private String zip;
    private String add1;
    private String add2;
    private String priInstitution;
    private String altInstitution;
    private String priInstCountry;
    private String altInstCountry;
    private String userIdentity;
    private String userPassword;
    private String cnfPassword;
    private String userRole;
    private String createdBy;
    private String createdDate;
    private String loginStatus;
    private String userState, altState, userAgreementStatus, userAgreementDate;
    private String lastAccessedTime = "";

    public UserRegistration() {
        lastName = firstName = middleName = salutation = nameSuffix = zip = userCity = altCity = userState = altState = userCountry = altCountry = "";
        primaryEmail = altEmail1 = altEmail2 = altEmail3 = altEmail4 = priInstitution = altInstitution = priInstCountry = altInstCountry = "";
        primaryMobile = altMobile = userIdentity = userPassword = cnfPassword = "";
        userRole = "USER";
        userAgreementStatus = loginStatus = "F";
        createdBy = "";
        action = "REG";
        add1 = "";
        add2 = "";

    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        lastName = lastName == null ? "" : lastName;
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        firstName = firstName == null ? "" : firstName;
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        middleName = middleName == null ? "" : middleName;
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the salutation
     */
    public String getSalutation() {
        salutation = salutation == null ? "" : salutation;
        return salutation;
    }

    /**
     * @param salutation the salutation to set
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the nameSuffix
     */
    public String getNameSuffix() {
        nameSuffix = nameSuffix == null ? "" : nameSuffix;
        return nameSuffix;
    }

    /**
     * @param nameSuffix the nameSuffix to set
     */
    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    /**
     * @return the primaryEmail
     */
    public String getPrimaryEmail() {
        primaryEmail = primaryEmail == null ? "" : primaryEmail;
        return primaryEmail;
    }

    /**
     * @param primaryEmail the primaryEmail to set
     */
    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    /**
     * @return the altEmail1
     */
    public String getAltEmail1() {
        altEmail1 = altEmail1 == null ? "" : altEmail1;
        return altEmail1;
    }

    /**
     * @param altEmail1 the altEmail1 to set
     */
    public void setAltEmail1(String altEmail1) {
        this.altEmail1 = altEmail1;
    }

    /**
     * @return the altEmail2
     */
    public String getAltEmail2() {
        altEmail2 = altEmail2 == null ? "" : altEmail2;
        return altEmail2;
    }

    /**
     * @param altEmail2 the altEmail2 to set
     */
    public void setAltEmail2(String altEmail2) {
        this.altEmail2 = altEmail2;
    }

    /**
     * @return the altEmail3
     */
    public String getAltEmail3() {
        altEmail3 = altEmail3 == null ? "" : altEmail3;
        return altEmail3;
    }

    /**
     * @param altEmail3 the altEmail3 to set
     */
    public void setAltEmail3(String altEmail3) {
        this.altEmail3 = altEmail3;
    }

    /**
     * @return the altEmail4
     */
    public String getAltEmail4() {
        altEmail4 = altEmail4 == null ? "" : altEmail4;
        return altEmail4;
    }

    /**
     * @param altEmail4 the altEmail4 to set
     */
    public void setAltEmail4(String altEmail4) {
        this.altEmail4 = altEmail4;
    }

    /**
     * @return the primaryMobile
     */
    public String getPrimaryMobile() {
        primaryMobile = primaryMobile == null ? "" : primaryMobile;
        return convertToInternationalFormat(primaryMobile);
        //return primaryMobile;
    }

    /**
     * @param primaryMobile the primaryMobile to set
     */
    public void setPrimaryMobile(String primaryMobile) {
        this.primaryMobile = primaryMobile;
    }

    /**
     * @return the altMobile
     */
    public String getAltMobile() {
        altMobile = altMobile == null ? "" : altMobile;
        altMobile = convertToInternationalFormat(altMobile);
        return altMobile;
    }

    /**
     * @param altMobile the altMobile to set
     */
    public void setAltMobile(String altMobile) {
        this.altMobile = altMobile;
    }

    /**
     * @return the userCity
     */
    public String getUserCity() {
        userCity = userCity == null ? "" : userCity;
        return userCity;
    }

    /**
     * @param userCity the userCity to set
     */
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    /**
     * @return the altCity
     */
    public String getAltCity() {
        altCity = altCity == null ? "" : altCity;
        return altCity;
    }

    /**
     * @param altCity the altCity to set
     */
    public void setAltCity(String altCity) {
        this.altCity = altCity;
    }

    /**
     * @return the userCountry
     */
    public String getUserCountry() {
        userCountry = userCountry == null ? "" : userCountry;
        return userCountry;
    }

    /**
     * @param userCountry the userCountry to set
     */
    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    /**
     * @return the altCountry
     */
    public String getAltCountry() {
        altCountry = altCountry == null ? "" : altCountry;
        return altCountry;
    }

    /**
     * @param altCountry the altCountry to set
     */
    public void setAltCountry(String altCountry) {
        this.altCountry = altCountry;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        zip = zip == null ? "" : zip;
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the priInstitution
     */
    public String getPriInstitution() {
        priInstitution = priInstitution == null ? "" : priInstitution;
        return priInstitution;
    }

    /**
     * @param priInstitution the priInstitution to set
     */
    public void setPriInstitution(String priInstitution) {
        this.priInstitution = priInstitution;
    }

    /**
     * @return the altInstitution
     */
    public String getAltInstitution() {
        altInstitution = altInstitution == null ? "" : altInstitution;
        return altInstitution;
    }

    /**
     * @param altInstitution the altInstitution to set
     */
    public void setAltInstitution(String altInstitution) {
        this.altInstitution = altInstitution;
    }

    /**
     * @return the priInstCountry
     */
    public String getPriInstCountry() {
        priInstCountry = priInstCountry == null ? "" : priInstCountry;
        return priInstCountry;
    }

    /**
     * @param priInstCountry the priInstCountry to set
     */
    public void setPriInstCountry(String priInstCountry) {
        this.priInstCountry = priInstCountry;
    }

    /**
     * @return the altInstCountry
     */
    public String getAltInstCountry() {
        altInstCountry = altInstCountry == null ? "" : altInstCountry;
        return altInstCountry;
    }

    /**
     * @param altInstCountry the altInstCountry to set
     */
    public void setAltInstCountry(String altInstCountry) {
        this.altInstCountry = altInstCountry;
    }

    /**
     * @return the userIdentity
     */
    public String getUserIdentity() {
        userIdentity = userIdentity == null ? "" : userIdentity;
        return userIdentity;
    }

    /**
     * @param userIdentity the userIdentity to set
     */
    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        userPassword = userPassword == null ? "" : userPassword;
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the cnfPassword
     */
    public String getCnfPassword() {
        return cnfPassword;
    }

    /**
     * @param cnfPassword the cnfPassword to set
     */
    public void setCnfPassword(String cnfPassword) {
        this.cnfPassword = cnfPassword;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        userRole = userRole == null ? "" : userRole;
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        createdBy = createdBy == null ? "" : createdBy;
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public String getCreatedDate() {
        createdDate = createdDate == null ? "" : createdDate;
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public static String[] countries = new String[]{"--Select--",
        "Afghanistan",
        "Åland Islands",
        "Albania",
        "Algeria",
        "American Samoa",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antarctica",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Bouvet Island",
        "Brazil",
        "British Indian Ocean Territory",
        "Brunei Darussalam",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",
        "Cayman Islands",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Christmas Island",
        "Cocos (Keeling) Islands",
        "Colombia",
        "Comoros",
        "Congo",
        "Congo, The Democratic Republic of The",
        "Cook Islands",
        "Costa Rica",
        "Cote D'ivoire",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands (Malvinas)",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Guiana",
        "French Polynesia",
        "French Southern Territories",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guadeloupe",
        "Guam",
        "Guatemala",
        "Guernsey",
        "Guinea",
        "Guinea-bissau",
        "Guyana",
        "Haiti",
        "Heard Island and Mcdonald Islands",
        "Holy See (Vatican City State)",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran, Islamic Republic of",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Korea, Democratic People's Republic of",
        "Korea, Republic of",
        "Kuwait",
        "Kyrgyzstan",
        "Lao People's Democratic Republic",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libyan Arab Jamahiriya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macao",
        "Macedonia, The Former Yugoslav Republic of",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Martinique",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Mexico",
        "Micronesia, Federated States of",
        "Moldova, Republic of",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Myanmar",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Niue",
        "Norfolk Island",
        "Northern Mariana Islands",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Palestinian Territory, Occupied",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Pitcairn",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Reunion",
        "Romania",
        "Russian Federation",
        "Rwanda",
        "Saint Helena",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Pierre and Miquelon",
        "Saint Vincent and The Grenadines",
        "Samoa",
        "San Marino",
        "Sao Tome and Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Georgia and The South Sandwich Islands",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Svalbard and Jan Mayen",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syrian Arab Republic",
        "Taiwan, Province of China",
        "Tajikistan",
        "Tanzania, United Republic of",
        "Thailand",
        "Timor-leste",
        "Togo",
        "Tokelau",
        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks and Caicos Islands",
        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "United States Minor Outlying Islands",
        "Uruguay",
        "Uzbekistan",
		"Vanuatu",
        "Venezuela",
        "Viet Nam",
        "Virgin Islands, British",
        "Virgin Islands, U.S.",
        "Wallis and Futuna",
        "Western Sahara",
        "Yemen",
        "Zambia",
        "Zimbabwe"};
    public static String[] codes = new String[]{"",
        "AF",
        "AX",
        "AL",
        "DZ",
        "AS",
        "AD",
        "AO",
        "AI",
        "AQ",
        "AG",
        "AR",
        "AM",
        "AW",
        "AU",
        "AT",
        "AZ",
        "BS",
        "BH",
        "BD",
        "BB",
        "BY",
        "BE",
        "BZ",
        "BJ",
        "BM",
        "BT",
        "BO",
        "BA",
        "BW",
        "BV",
        "BR",
        "IO",
        "BN",
        "BG",
        "BF",
        "BI",
        "KH",
        "CM",
        "CA",
        "CV",
        "KY",
        "CF",
        "TD",
        "CL",
        "CN",
        "CX",
        "CC",
        "CO",
        "KM",
        "CG",
        "CD",
        "CK",
        "CR",
        "CI",
        "HR",
        "CU",
        "CY",
        "CZ",
        "DK",
        "DJ",
        "DM",
        "DO",
        "EC",
        "EG",
        "SV",
        "GQ",
        "ER",
        "EE",
        "ET",
        "FK",
        "FO",
        "FJ",
        "FI",
        "FR",
        "GF",
        "PF",
        "TF",
        "GA",
        "GM",
        "GE",
        "DE",
        "GH",
        "GI",
        "GR",
        "GL",
        "GD",
        "GP",
        "GU",
        "GT",
        "GG",
        "GN",
        "GW",
        "GY",
        "HT",
        "HM",
        "VA",
        "HN",
        "HK",
        "HU",
        "IS",
        "IN",
        "ID",
        "IR",
        "IQ",
        "IE",
        "IM",
        "IL",
        "IT",
        "JM",
        "JP",
        "JE",
        "JO",
        "KZ",
        "KE",
        "KI",
        "KP",
        "KR",
        "KW",
        "KG",
        "LA",
        "LV",
        "LB",
        "LS",
        "LR",
        "LY",
        "LI",
        "LT",
        "LU",
        "MO",
        "MK",
        "MG",
        "MW",
        "MY",
        "MV",
        "ML",
        "MT",
        "MH",
        "MQ",
        "MR",
        "MU",
        "YT",
        "MX",
        "FM",
        "MD",
        "MC",
        "MN",
        "ME",
        "MS",
        "MA",
        "MZ",
        "MM",
        "NA",
        "NR",
        "NP",
        "NL",
        "AN",
        "NC",
        "NZ",
        "NI",
        "NE",
        "NG",
        "NU",
        "NF",
        "MP",
        "NO",
        "OM",
        "PK",
        "PW",
        "PS",
        "PA",
        "PG",
        "PY",
        "PE",
        "PH",
        "PN",
        "PL",
        "PT",
        "PR",
        "QA",
        "RE",
        "RO",
        "RU",
        "RW",
        "SH",
        "KN",
        "LC",
        "PM",
        "VC",
        "WS",
        "SM",
        "ST",
        "SA",
        "SN",
        "RS",
        "SC",
        "SL",
        "SG",
        "SK",
        "SI",
        "SB",
        "SO",
        "ZA",
        "GS",
        "ES",
        "LK",
        "SD",
        "SR",
        "SJ",
        "SZ",
        "SE",
        "CH",
        "SY",
        "TW",
        "TJ",
        "TZ",
        "TH",
        "TL",
        "TG",
        "TK",
        "TO",
        "TT",
        "TN",
        "TR",
        "TM",
        "TC",
        "TV",
        "UG",
        "UA",
        "AE",
        "GB",
        "US",
        "UM",
        "UY",
        "UZ",
		"VU",
        "VE",
        "VN",
        "VG",
        "VI",
        "WF",
        "EH",
        "YE",
        "ZM",
        "ZW"};

    /**
     * @return the userState
     */
    public String getUserState() {
        userState = userState == null ? "" : userState;
        return userState;
    }

    /**
     * @param userState the userState to set
     */
    public void setUserState(String userState) {
        this.userState = userState;
    }

    /**
     * @return the altState
     */
    public String getAltState() {
        altState = altState == null ? "" : altState;
        return altState;
    }

    /**
     * @param altState the altState to set
     */
    public void setAltState(String altState) {
        this.altState = altState;
    }

    /**
     * @return the userAgreementStatus
     */
    public String getUserAgreementStatus() {
        userAgreementStatus = userAgreementStatus == null ? "" : userAgreementStatus;
        return userAgreementStatus;
    }

    /**
     * @param userAgreementStatus the userAgreementStatus to set
     */
    public void setUserAgreementStatus(String userAgreementStatus) {
        this.userAgreementStatus = userAgreementStatus;
    }

    /**
     * @return the userAgreementDate
     */
    public String getUserAgreementDate() {
        userAgreementDate = userAgreementDate == null ? "" : userAgreementDate;
        return userAgreementDate;
    }

    /**
     * @param userAgreementDate the userAgreementDate to set
     */
    public void setUserAgreementDate(String userAgreementDate) {
        this.userAgreementDate = userAgreementDate;
    }

    /**
     * @return the loginStatus
     */
    public String getLoginStatus() {
        loginStatus = loginStatus == null ? "" : loginStatus;
        return loginStatus;
    }

    /**
     * @param loginStatus the loginStatus to set
     */
    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
    private static final Pattern mobileNumberFormat = Pattern.compile("^\\+?[0-9. ()-]{10,20}$");              //Pattern.compile("^([0-9]){20}$");

    private boolean isEmpty(String s) {
        if (s != null && s.trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public String validate() {
        StringBuffer msg = new StringBuffer();
        if (isEmpty(lastName)) {
            msg.append("Last Name is required!<br>");
        }
        if (isEmpty(firstName)) {
            msg.append("First Name is required!<br>");
        }
        if (isEmpty(primaryEmail)) {
            msg.append("Primary Email is required!<br>");
        }
        if (!rfc2822.matcher(primaryEmail).matches()) {
            msg.append("Please enter valid email for Primary Email!<br>");
        }
        if (!isEmpty(altEmail1) && !rfc2822.matcher(altEmail1).matches()) {
            msg.append("Please enter valid email for Alternate Email 1<br>");
        }
        if (!isEmpty(altEmail2) && !rfc2822.matcher(altEmail2).matches()) {
            msg.append("Please enter valid email for Alternate Email 2<br>");
        }
        if (!isEmpty(altEmail3) && !rfc2822.matcher(altEmail3).matches()) {
            msg.append("Please enter valid email for Alternate Email 3<br>");
        }
        if (!isEmpty(altEmail4) && !rfc2822.matcher(altEmail4).matches()) {
            msg.append("Please enter valid email for Alternate Email 4<br>");
        }
        /*
         * if (isEmpty(primaryMobile)) { msg.append("Primary Mobile number is
         * required!<br>"); }
         */
        if (!isEmpty(primaryMobile) && !mobileNumberFormat.matcher(primaryMobile).matches()) {
            msg.append("Please enter valid mobile number for PrimaryMobile. <br>");
        }
        if (!isEmpty(altMobile) && !mobileNumberFormat.matcher(altMobile).matches()) {
            msg.append("Please enter valid mobile number for Alternate Mobile. <br>");
        }
        if (isEmpty(userCity)) {
            msg.append("User City is required!<br>");
        }
       /* if (isEmpty(userState)) {
            msg.append("User State is required!<br>");
        }*/
        if (isEmpty(userCountry)) {
            msg.append("User Country is required!<br>");
        }
        if (!getAction().equalsIgnoreCase("EDIT") && !getAction().equalsIgnoreCase("UPDATE")) {
            if (isEmpty(userIdentity)) {
                msg.append("User Identity is required!<br>");
            }
            if (isEmpty(userPassword)) {
                msg.append("User Password is required!<br>");
            }
            if (isEmpty(cnfPassword)) {
                msg.append("Confirm Password is required!<br>");
            }
            if (!isEmpty(userPassword) && !isEmpty(cnfPassword) && !(userPassword.equals(cnfPassword))) {
                msg.append("Passwords do not match!<br>");
            }
        }


        if (isEmpty(msg.toString())) {
            msg.append("VALID");
        }

        return msg.toString();
    }

    public String saveRecord() throws Exception {
        UserDAO dao = new UserDAO();
        String msg = "Error: Contact Administrator";
        if (getAction().equalsIgnoreCase("REG") || getAction().equalsIgnoreCase("ADD")) {
            msg = dao.registerUser(this);
        } else if (getAction().equalsIgnoreCase("EDIT") || getAction().equalsIgnoreCase("UPDATE")) {
            msg = dao.updateUser(this);
        }
        return msg;
    }

    /**
     * @return the action
     */
    public String getAction() {
        action = action == null ? "" : action;
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the lastAccessedTime
     */
    public String getLastAccessedTime() {
        return lastAccessedTime;
    }

    /**
     * @param lastAccessedTime the lastAccessedTime to set
     */
    public void setLastAccessedTime(String lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    private String convertToInternationalFormat(String phone) {
        String number = "";
        if (phone.trim().length() > 0) {
            number = phone.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
            int i = 0;
            if ((i = number.indexOf(".")) > 0) {
                number = number.substring(0, i) + number.substring(i + 1);
            }
            if (!phone.startsWith("+")) {
                number = "+" + number;
            }
        }
        return number;
    }

    public static void main(String[] args) {
        String[] phoneNumbersToValidate = {
            "2-309-778-4234", //invalid
            "1-309-778-4423",
            "309-778-4235",
            "3097784234",
            "309.778.4234",
            "01-309-798-4234",//invalid
            "001-309-798-4234", //invalid
            "0-309-798-4234",
            "+1-309-798-4235",
            "1-3097980577",
            "1-309.7980578",
            "1-(309)-788-8978",
            "+919849203571",
            "+1617-671-1208",
            "+44 (7958) 454227"
        };
        UserRegistration r = new UserRegistration();
        for (int index = 0; index < phoneNumbersToValidate.length; index++) {
            String number = phoneNumbersToValidate[index];
            //  boolean isValid=isAValidUSPhoneNumber(phoneNumbersToValidate[index]);
            String regex = "^\\+?[0-9. ()-]{10,20}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(number);
            boolean isValid = matcher.matches();

            System.out.println(number + " : " + r.convertToInternationalFormat(phoneNumbersToValidate[index]) + "::" + isValid);
        }
    }

    /**
     * @return the add1
     */
    public String getAdd1() {
        add1 = add1 == null ? "" : add1;
        return add1;
    }

    /**
     * @param add1 the add1 to set
     */
    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    /**
     * @return the add2
     */
    public String getAdd2() {
        add2 = add2 == null ? "" : add2;
        return add2;
    }

    /**
     * @param add2 the add2 to set
     */
    public void setAdd2(String add2) {
        this.add2 = add2;
    }
}
