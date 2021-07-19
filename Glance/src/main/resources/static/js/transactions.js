/*
    1. Will handle interpretation of transactions string into an amendable and displayable format

    2. Will provide dynamic webpage afforances to transactions.html
*/


//Lets parse transactions here
//Exact quantity names we are interested in from plaid
const quantities = [" date: ", " merchantName: ", " amount: ", " category: ", " pending: " ]


let sample = [
    "class Transaction {\n    transactionType: special\n    transactionId: 18vAW7vVj8ILQNe7X7bgtpLvKZWn4GtQVQqBd\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: in store\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: Uber 063015 SF**POOL**\n    merchantName: Uber\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: null\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-07-09\n    datetime: null\n    categoryId: 22016000\n    category: [Travel, Taxi]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: 5.4\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}",
    "class Transaction {\n    transactionType: special\n    transactionId: LX1APx1a7XSBL6nbMbrghN5ZvjKB7MuLBLewX\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: other\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: United Airlines\n    merchantName: United Airlines\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: null\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-07-07\n    datetime: null\n    categoryId: 22001000\n    category: [Travel, Airlines and Aviation Services]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: -500.0\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}",
    "class Transaction {\n    transactionType: place\n    transactionId: p4j6AkjD84UARmVE3EwJhKW87aVeGzTDeDJax\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: in store\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: McDonald's\n    merchantName: McDonald's\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: 3322\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-07-06\n    datetime: null\n    categoryId: 13005032\n    category: [Food and Drink, Restaurants, Fast Food]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: 12.0\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}",
    "class Transaction {\n    transactionType: place\n    transactionId: oaP6ydPDgaSmb7EGBGQ5hBEqDx6W3rCXAXpEl\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: in store\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: Starbucks\n    merchantName: Starbucks\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: null\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-07-06\n    datetime: null\n    categoryId: 13005043\n    category: [Food and Drink, Restaurants, Coffee Shop]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: 4.33\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}",
    "class Transaction {\n    transactionType: place\n    transactionId: gqzD7lzXeqsAw6nE4EXqhZpMr7nLWbul9lLWb\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: in store\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: SparkFun\n    merchantName: Sparkfun\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: null\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-07-05\n    datetime: null\n    categoryId: 13005000\n    category: [Food and Drink, Restaurants]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: 89.4\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}",
    "class Transaction {\n    transactionType: special\n    transactionId: 8X5oZy5mLXS9l5JwGwxVTKBg1Vn6MeTx5xZqn\n    accountOwner: null\n    pendingTransactionId: null\n    pending: false\n    paymentChannel: in store\n    paymentMeta: class PaymentMeta {\n        referenceNumber: null\n        ppdId: null\n        payee: null\n        byOrderOf: null\n        payer: null\n        paymentMethod: null\n        paymentProcessor: null\n        reason: null\n    }\n    name: Uber 072515 SF**POOL**\n    merchantName: Uber\n    location: class Location {\n        address: null\n        city: null\n        region: null\n        postalCode: null\n        country: null\n        lat: null\n        lon: null\n        storeNumber: null\n    }\n    authorizedDate: null\n    authorizedDatetime: null\n    date: 2021-06-22\n    datetime: null\n    categoryId: 22016000\n    category: [Travel, Taxi]\n    unofficialCurrencyCode: null\n    isoCurrencyCode: USD\n    amount: 6.33\n    accountId: bn5RyP5X4nhA1JEGBGwoh9pGmaMnG7IGMLKmX\n    transactionCode: null\n}"
]

//console.log("Viewables: \n" + parseTransactions(sample)[0]);

/*
    Building function transaction:
    plaidName -> ourName description
        merchantName -> merchantName
        amount -> amount
        pending -> isPending            //is transaction pending T/F
        date -> date                    //date of purchase, im assuming
        category -> categories          // e.g. 'Food and Drink', Restuarants, Travel, taxi, comma seperated
*/
function Transaction(date, merchantName, amount, categories, isPending) {
    this.date = date;
    this.merchantName = merchantName;
    this.amount = amount;
    this.categories = categories;
    this.isPending = isPending;
}

//Now build function for parsing JSON Array transactions string
function parseTransactions(list) {
    let viewables = [];

    console.log("parsing transactions:\n\n");
    list.map( (plaidTx) => { viewables.push(new Transaction(
        pullValue(plaidTx, quantities[0]),
        pullValue(plaidTx, quantities[1]),
        pullValue(plaidTx, quantities[2]),
        pullValue(plaidTx, quantities[3]),
        pullValue(plaidTx, quantities[4]),
    ) 
    )})
    //End list MAP

    return viewables;
}

//Second pair of helper functions for "locating" element in the lengthly String
function pullValue(plaidTx, quantity) {

    //match quantity in plaidTx
    //pull value out until we reach '\n'
    let ex = `${quantity}.*\\n`
    let regEx = new RegExp(ex, "g");
    //console.log("Our regX: " + regEx);

    //find match and cleanup
    let temp = plaidTx.match(regEx);
    temp[0] = temp[0].replace(new RegExp(`${quantity}`, "g"), "");
    temp[0] = temp[0].replace(new RegExp('\n', "g"), "");

    //return value
    //console.log("Returning: " + temp);
    return temp[0];
}



/* DYNAMIC WEB BASED JS DOWN HERE */

window.onload = function() {
    getDataOnLoad();
}



//Await data from backend
    //calls functions to build pages in js
async function getDataOnLoad() {


    //Get Params array as splitting cookie we hold
    let params = document.cookie.split("; ");
    const userID = params[0];
    const  isLogged = params[1];
    const  accIDs = params[2].split("accounts=")[1].split("---");
    const activeAcc = params[3].split("activeAccount=")[1];
    const accessToken = params[4];

    console.log("activeAcc in cookie= " + activeAcc);

    //Get Account Overview page link
    let  overviewURL = `/overview.html`;
    let getAccountsURL = `/getAccounts?${userID}`;
    console.log("url = " + overviewURL);

    //Get Data for All accounts to get Names:
    let responseAllAccs = await fetch(getAccountsURL);
    let allAccounts = await responseAllAccs.json();


    let validAccts = [];
    for (let i = 0; i < allAccounts.length; i++) {
        if(allAccounts[i].match(/depository/g) != null) {
        validAccts.push(allAccounts[i]);
        }
    }


    console.log(allAccounts);
    //let accountsArr = allAcounts.split(",");

    
    //multi dim arrat contains [[accLink1, accName1], [accLink2...]]
    accountLinks = [];
    validAccts.map( stringAcc => {
        let acc = new Account(stringAcc);
        accountLinks.push([acc.account_id, acc.name]);
        //need an onclick event to change the cookie
    });
    //console.log("accountLinks array: " + accountLinks);
    addAccountLinks(overviewURL, accountLinks);

    
    //Get account details for THIS account
    let accountURL = "/getAccount";
    let responseAcc = await fetch(accountURL);
    let accountDetails = await responseAcc.json();

    //console.log("Single account data: " + JSON.stringify(accountDetails));
    let accData = new AccountJSON(accountDetails);
    buildAccDetails(accData);
    
    
    //Get account transactions for THIS account
    let transactionURL = "/getTransactions";
    let responseTrans = await fetch(transactionURL);
    let trans = await responseTrans.json();
    console.log("Single transaction data: " + trans);

    let viewables = parseTransactions(trans);
    //console.log("MADE IT PAST PARSE TRANSACTIONS" + viewables);
    buildTable(viewables);
    
}



//Add accounts dynamically to navbar
    // accountsOverviewURL - string URL back to accounts overview for this user
    // 
async function addAccountLinks(accountsOverviewURL, accountLinks) {

    //"Accounts" section header links back to overview
    document.getElementById("accounts-overview-link").href = accountsOverviewURL;

    //Each "Account k" links to that account page
    let htmlList = document.getElementById("list-for-acc-links");
    accountLinks.forEach(accLink => {
        htmlList.innerHTML = htmlList.innerHTML +
         `<li class="acc-link-list"><a href="/accountDetails.html" 
          class="acc-link"
          onclick="accDetailsOnClick('${accLink[0]}')"> 
          ${accLink[1]}</a></li>`;
    });
}

//onlick function with parameters for sending user to different acc details pages
function accDetailsOnClick(newAcc) {
    //console.log("Setting cookie with new val: " + newAcc);
    document.cookie = `activeAccount=${newAcc}`;

    let params = document.cookie.split("; ");
    const userID = params[0];
    const  isLogged = params[1];
    const  accIDs = params[2].split("accounts=")[1].split("---");
    const activeAcc = params[3];

    console.log("New cookie val: " + activeAcc);
}




/********* Builds Account details Section ************/

/*
    Constructor for Account consists of Mappings:
    plaid -> our Name,

    account_id -> account_id (not displayed)
    balances[0].stringify().current -> "Current Balance"
    "balances" -> JSON.parse(balances[0].current) -> "Current Balance"
    "balances" -> JSON.parse(balances[0].availible) -> "Availible Balance"
    "name" -> "Account Name"
    "Official Name" -> "Account Official Name"
    "type" -> "Account Type"
*/
function Account(accString) {

    this.account_id = pullValue(accString, accQuantities[0]);
    this.availible = pullValue(accString, accQuantities[1]);
    this.current = pullValue(accString, accQuantities[2]);
    this.name = pullValue(accString, accQuantities[3]);
    this.officialName = pullValue(accString, accQuantities[4]);
    this.type = pullValue(accString, accQuantities[5]);
    this.subtype = pullValue(accString, accQuantities[6]);
}
//END ACC CONSTRUCTOR

function AccountJSON(accJSON) {

    this.account_id = accJSON.accountId;
    this.available = accJSON.balances.available;
    this.current = accJSON.balances.current;
    this.name = accJSON.name;
    this.officialName = accJSON.officialName;
    this.type = accJSON.type;
    this.subtype = accJSON.subtype;
}
//END ACC CONSTRUCTOR

const accQuantities = ["accountId: ", "available: ", "current: ", "name: ",
         "officialName: ", "type: ", "subtype: "];

const detailNames = ["Avalible Balance: ", "Current Balance: ", "Name: ", 
        "Type: "];

//build account details section from data
function buildAccDetails(accData) {
    
    console.log("in buildACc details" + accData);
    let detailsList = document.getElementById("acc-details-list");
    let values = [];
    Object.keys(accData).map(el => { values.push(accData[el]) });

    let k = 0;
    for (let i = 1; i < 5; i++) {
        if(i === 4) { continue; }
        detailsList.innerHTML = detailsList.innerHTML +
         `<li class="acc-detail"><strong>${detailNames[k++]}</strong> ${values[i]}</li>`;
    }

    //aggregate type and subtype
    detailsList.innerHTML = detailsList.innerHTML +
         `<li class="acc-detail"><strong>${detailNames[k]}</strong> ${values[k+2]} - ${values[k+3]}</li>`;


    //Add official account name as title for summary table
    document.getElementById("summary-table-title").innerHTML = values[4];
}

function buildTable(transData) {
    /*
        Builds table of transactions dynamically up to 
        a certain specified number
    */
   const maxTrans = 10;
    console.log("MADE IT INTO BUILD TABLE");
   let table = document.getElementById("summary-table");
    transData.forEach(data => {
        table.innerHTML = table.innerHTML +
         `<tr class="trans-table-row">
            <td>${data.date}</td>
            <td>${data.merchantName}</td>
            <td>${data.amount}</td>
            <td>${data.categories}</td>
            <td>${data.isPending}</td>
         </tr>`;
    });

}













