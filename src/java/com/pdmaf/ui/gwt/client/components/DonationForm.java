package com.pdmaf.ui.gwt.client.components;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.pdmaf.ui.gwt.client.PDMAFWebMessages;


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 2, 2009
 * Time: 11:27:23 AM
 *
 */
public class DonationForm extends Composite {
    private static final PDMAFWebMessages MESSAGES = (PDMAFWebMessages) GWT.create(PDMAFWebMessages.class);

//    private TextItem firstname;
//    private TextItem lastname;
//    private TextItem email;
//    private TextItem address1;
//    private TextItem address2;
//    private TextItem city;
//    private TextItem state;
//    private TextItem zipcode;
//    private SelectItem country;
//    private TextItem phone;
//    private ComboBoxItem creditcardType;
//    private TextItem creditcardName;
//    private TextItem creditcardNumber;
//    private DateItem expirationDate;
//    private TextItem donationAmount;
//
//    public DonationForm() {
//        super();
//        final DynamicForm form = new DynamicForm();
//        form.setWidth(350);
//
//        firstname = new TextItem("First name");
//        firstname.setName("firstname");
//        firstname.setTitle("First Name");
//        firstname.setType("text");
//        firstname.setLength(50);
//
//        lastname = new TextItem("Last name");
//        lastname.setTitle("Last Name");
//        lastname.setName("lastname");
//        lastname.setType("text");
//        lastname.setLength(50);
//
//        email = new TextItem("Email");
//        email.setName("email");
//        email.setTitle("Email");
//        email.setType("email");
//        email.setLength(50);
//
//        address1 = new TextItem("Addess 1");
//        address1.setName("address1");
//        address1.setTitle("Address 1");
//        address1.setType("text");
//        address1.setLength(50);
//
//        address2 = new TextItem("Addess 2");
//        address2.setName("address2");
//        address2.setTitle("Address 2");
//        address2.setType("text");
//        address2.setLength(50);
//
//        city = new TextItem("City");
//        city.setName("city");
//        city.setTitle("City");
//        city.setType("text");
//        city.setLength(50);
//
//        state = new TextItem("State");
//        state.setName("state");
//        state.setTitle("State");
//        state.setType("text");
//        state.setLength(50);
//
//        zipcode = new TextItem("Zip Code");
//        zipcode.setName("zipcode");
//        zipcode.setTitle("Zip Code");
//        zipcode.setType("text");
//        zipcode.setLength(50);
//
//        country = new SelectItem("Country");
//        country.setName("country");
//        country.setTitle("Country");
//        country.setLeft(50);
//
//        phone = new TextItem("Telephone");
//        phone.setName("phone");
//        phone.setTitle("Telephone");
//        phone.setType("text");
//        phone.setLength(50);
//
//        SectionItem personalInfo = new SectionItem("Personal Information");
//        personalInfo.setDefaultValue("Personal Information");
//        personalInfo.setSectionExpanded(true);
//        personalInfo.setItemIds("firstname", "lastname", "email", "address1", "address2", "city", "state",
//                "zipcode", "country", "phone");
//
//        creditcardType = new ComboBoxItem();
//        creditcardType.setTitle("Creditcard Type");
//        creditcardType.setName("creditcardType");
//        creditcardType.setType("comboBox");
//        creditcardType.setValueMap("Visa", "Master", "American Express", "Discover", "Diner Club");
//
//        creditcardName = new TextItem("Creditcard Number");
//        creditcardName.setName("creditcardName");
//        creditcardName.setTitle("Name on Creditcard");
//        creditcardName.setType("text");
//        creditcardName.setLength(50);
//
//        creditcardNumber = new TextItem("Creditcard Number");
//        creditcardNumber.setName("creditcardNumber");
//        creditcardNumber.setTitle("Creditcard Number");
//        creditcardNumber.setType("Integer");
//        creditcardNumber.setLength(50);
//
//        expirationDate = new DateItem();
//        expirationDate.setTitle("Expiration Date");
//        expirationDate.setName("expirationDate");
//
//        SectionItem creditcardinfo = new SectionItem("Creditcard Information");
//        creditcardinfo.setDefaultValue("Creditcard Information");
//        creditcardinfo.setSectionExpanded(false);
//        creditcardinfo.setItemIds("creditcardType", "creditcardName","creditcardNumber", "expirationDate");
//
//        donationAmount = new TextItem("Donation Amount $");
//        donationAmount.setName("donationAmount");
//        donationAmount.setTitle("Donation Amount");
//        donationAmount.setType("Integer");
//        donationAmount.setLength(50);
//
//        LookupCharityOrganizationBox charityOrganization = LookupCharityOrganizationBox.instance();
//        charityOrganization.setName("charityOrganization");
//        charityOrganization.setTitle("Charity Organization");
//        charityOrganization.setLength(50);
//        charityOrganization.setType("text");
//
//        SectionItem donationInfo = new SectionItem("Donation Information");
//        donationInfo.setDefaultValue("Donation Information");
//        donationInfo.setSectionExpanded(false);
//        donationInfo.setItemIds("donationAmount", "charityOrganization");
//
//        form.setFields(personalInfo, firstname, lastname, email, address1, address2, city, state, zipcode,
//                country, phone, creditcardinfo, creditcardType, creditcardName, creditcardNumber, expirationDate,
//                donationInfo, donationAmount, charityOrganization);
//        VLayout vLayout = new VLayout(5);
//        IButton makeDonationButton = new IButton("Donate");
//        makeDonationButton.addClickHandler(new ClickHandler(){
//            public void onClick(ClickEvent clickEvent) {
//
//            }
//        });
//
//        IButton cancelButton = new IButton("Cancel");
//        cancelButton.addClickHandler(new ClickHandler(){
//            public void onClick(ClickEvent clickEvent) {
//
//            }
//        });
//
//        vLayout.addMember(form);
//        HStack hStack = new HStack();
//        hStack.setHeight(50);
//        hStack.setMembersMargin(5);
//        hStack.setLayoutMargin(10);
//        hStack.addMember(makeDonationButton);
//        hStack.addMember(cancelButton);
//        vLayout.addMember(hStack);
//        //adding this form to the page
//        RootPanel.get("donation-form").add(vLayout);
//
//        //now create the charity organization submit form and add it to the join our network section
//        HLayout layout = new HLayout();
//
//         final DynamicForm cForm = new DynamicForm();
//         cForm.setWidth(250);
//
//         TextItem usernameItem = new TextItem();
//         usernameItem.setTitle("Username");
//         usernameItem.setRequired(true);
//         usernameItem.setDefaultValue("bob");
//
//         TextItem emailItem = new TextItem();
//         emailItem.setTitle("Email");
//         emailItem.setRequired(true);
//         emailItem.setDefaultValue("bob@isomorphic.com");
//
//         TextItem passwordItem = new TextItem();
//         passwordItem.setTitle("Password");
//         passwordItem.setRequired(true);
//         passwordItem.setType("password");
//
//         TextItem password2Item = new TextItem();
//         password2Item.setTitle("Password again");
//         password2Item.setRequired(true);
//         password2Item.setType("password");
//
//         cForm.setFields(new FormItem[] {usernameItem, emailItem, passwordItem, password2Item});
//
//         IButton swapButton = new IButton("Swap titles");
//         swapButton.setLeft(300);
//         swapButton.addClickHandler(new ClickHandler() {
//             public void onClick(ClickEvent event) {
//             }
//         });
//
//         layout.addMember(cForm);
//         layout.addMember(swapButton);
//
//         RootPanel.get("charityorganization-form").add(layout);
//
//    }
//
//     private static class LookupCharityOrganizationBox extends TextItem {
//
//         private static LookupCharityOrganizationBox currentEditor;
//         private static Window window;
//
//         public static LookupCharityOrganizationBox instance() {
//            if (currentEditor == null) {
//                currentEditor = new LookupCharityOrganizationBox();
//            }
//
//            return currentEditor;
//         }
//
//         private static void popUpWindow() {
//             window = new Window();
//             window.setWidth(450);
//             window.setHeight(300);
//             window.setTitle("Charity Organization Lookup Window");
//             window.setShowMinimizeButton(false);
//             window.setIsModal(true);
//             window.centerInPage();
//             window.addCloseClickHandler(new CloseClickHandler() {
//                public void onCloseClick(CloseClientEvent event) {
//                     window.destroy();
//             }});
//             HStack hStack = new HStack();
//             hStack.setShowEdges(true);
//             hStack.setHeight(50);
//             hStack.setMembersMargin(5);
//             hStack.setLayoutMargin(10);
//
//             DynamicForm searchForm = new DynamicForm();
//             searchForm.setWidth(250);
//             searchForm.setHeight(100);
//
//             final TextItem searchBox = new TextItem();
//             searchBox.setName("Charity Organization Name");
//             searchBox.setRequired(true);
//             searchBox.setLength(100);
//
//             searchForm.setFields(searchBox);
//
//             IButton searchButton = new IButton("Search");
//             searchButton.addClickHandler(new ClickHandler() {
//                 public void onClick(ClickEvent event) {
//                     final Client client = new Client(Protocol.HTTP);
//                     client.get("http://localhost:8080/PDMAFWeb/resource/index", new Callback() {
//                         public void onEvent(Request request, Response response) {
//                               searchBox.setValue(response.getEntity().getText());
//                         }});
//                 }
//             });
//             hStack.setAlign(VerticalAlignment.BOTTOM);
//             hStack.addMember(searchForm);
//             hStack.addMember(searchButton);
//             window.addItem(hStack);
//         }
//
//         // set the specified value and dismiss the picker dialog
//         private static void seCurrentValue(String value) {
//             currentEditor.setValue(value);
//             window.destroy();
//         }
//
//         // show the picker dialog at the specified position
//         private static void showDialog(int left, int top) {
//             window.show();
//             window.moveTo(left, top);
//         }
//
//         public LookupCharityOrganizationBox() {
//             //use default trigger icon here. User can customize.
//             //[SKIN]/DynamicForm/default_formItem_icon.gif
//             FormItemIcon formItemIcon = new FormItemIcon();
//             setIcons(formItemIcon);
//
//             addIconClickHandler(new IconClickHandler() {
//                 public void onIconClick(IconClickEvent event) {
//
//                     // get global coordinates of the clicked picker icon
//                     Rectangle iconRect = getIconPageRect(event.getIcon());
//                     LookupCharityOrganizationBox.popUpWindow();
//                     // remember what editor is active, so we can set its value from the picker dialog
//                     LookupCharityOrganizationBox.currentEditor = LookupCharityOrganizationBox.this;
//
//                     // show the picker dialog
//                     LookupCharityOrganizationBox.showDialog(iconRect.getLeft(), iconRect.getTop());
//                 }
//             });
//         }
//     }
}
