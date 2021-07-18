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

console.log("Viewables: \n" + parseTransactions(sample)[0]);

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

    //return value
    console.log("Returning: " + temp);
    return temp[0];
}



/* DYNAMIC WEB BASED JS DOWN HERE */

window.onload = function() {
    let pageData = getDataOnLoad();

    //Sends link to overview page, links to all other accounts
    addAccountLinks(pageData[0], pageData[1]);
}



//Await data from backend
    //retains array [linkToAccountsOverview, [invididual account links], [accDetails], [accTransactions]]

    //pageData is the array (of arrays) we will return from this method
    let pageData = [];
async function getDataOnLoad() {


    //Get Params array as splitting cookie we hold
    let params = document.cookie.split("; ");
    const userID = params[0];
    const  isLogged = params[1];
    const  accIDs = params[2];
    const activeAcc = params[3];
    const accessToken = params[4];


    //Get Account Overview page link
    let overviewURL = `/getAccounts?${userID}`;
    console.log("url = " + overviewURL);
    pageData.push(overviewURL)

    //Get Individual account links
    let response = await fetch(overviewURL);
    accounts = await response.json();
    console.log("All account data: " + accounts);

    //multi dim arrat contains [[accLink1, accName1], [accLink2...]]
    accountLinks = [];
    accIDs.map( id => {
        accountLinks.push([`/getAccount?plaidAccID=${id}`, `Account ${id}`]);
    });
    pageData.push(accountLinks);

    //Get account details for THIS account
    let accountURL = `/getAccount?${activeAcc}`
    let responseAcc = await fetch(accountURL);
    let accountDetails = await responseAcc.json();
    console.log("Single account data: " + accountDetails);
    pageData.push(accountDetails);
    
    //Get account transactions for THIS account
    let transactionURL = `/getTransactions`
    let responseTrans = await fetch(tranasctionURL);
    trans = await responseTrans.json();
    console.log("Single transaction data: " + trans);
    pageData.push(trans);

    return pageData;
}




//Add accounts dynamically to navbar
    // accountsOverviewURL - string URL back to accounts overview for this user
    // 
function addAccountLinks(accountsOverviewURL, accountLinks) {

    //"Accounts" section header links back to overview
    document.getElementById("accounts-overview-link").href = accountsOverviewURL;

    //Each "Account k" links to that account page
    let htmlList = document.getElementById("list-for-acc-links");
    accountLinks.array.forEach(accLink => {
        htmlList.innerHTML = htmlList.innerHTML +
         `<li class="acc-link-list"><a href="${accLink[0]}" class="acc-link">${accLink[1]}</a></li>`;
    });

    //need to dom.innerHTML
}



//Adds transactions to transactions list and builds chart
function buildChart(data) {
    /*
        Pie chart maps expenditures to individual categories
    */


}


function buildTable(data) {
    /*
        Builds table of transactions dynamically up to 
        a certain specified number
    */
   const maxTrans = 10;

}


















