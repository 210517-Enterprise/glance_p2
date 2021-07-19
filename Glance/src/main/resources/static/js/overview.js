let user = null;
let token = null;
let accounts = [];

window.onload = function() {
  getAccounts();
}

let getAccounts = async function() {
  let params = document.cookie.split("; ");
  
  //Check if user attempted to bypass login
  if(!params[1]) {
	  // Redirect to /index.html
	window.location.replace("/index.html");
  }
  
  
  let url = `/getAccounts?${params[0]}`;
  console.log("url is being changed = " + url);
  console.log("PARAMS = " + params);
  let response = await fetch(url);
  accounts = await response.json();
  console.log(accounts);
  console.log(accounts[0]);
  console.log(accounts[0].balances);

  let validAccts = [];
  for (let i = 0; i < accounts.length; i++) {
    if(accounts[i].match(/depository/g) != null) {
      validAccts.push(accounts[i]);
    }
  }

  console.log(validAccts);
  loadAccountList(validAccts);

  let dataset = [];
  let names = [];

  for (let j = 0; j < validAccts.length; j++ ) {
     let temp = validAccts[j].match(/[^w](name:).*\n/g);
     let name = temp[0].replace(" ", "").replace("\n", "").substr(temp[0].indexOf(":"));
     temp = validAccts[j].match(/[^w](current:).*\n/g);
     let currentBal = temp[0].replace(" ", "").replace("\n", "").substr(temp[0].indexOf(":")) + "0";
     temp = validAccts[j].match(/[^w](available:).*\n/g);
     let availableBal = temp[0].replace(" ", "").replace("\n", "").substr(temp[0].indexOf(":")) + "0";
     if (availableBal.match(/(null).*/g) != null) {
       availableBal = "$0.00";
     }
     dataset.push(currentBal);
     names.push(name);

     let tRow = document.createElement("tr");
     tRow.innerHTML = `<td>${name}</td><td>${currentBal}</td><td>${availableBal}</td>`;
     document.getElementById("summaryTable").appendChild(tRow);
   }



               const data = {
                 labels: names,
                 datasets: [{
                   label: 'Account Balances',
                   data: dataset,
                   backgroundColor: [
                     '#F08030',
                     '#C03028',
                     '#6890F0',
                     '#A890F0',
                     '#78C850',
                     '#A040A0'
                   ],
                   hoverOffset: 8
                 }]
               };

               const config = {
                 type: 'pie',
                 data,
                 options: {}
               };

                 var myChart = new Chart(
                 document.getElementById('myChart'),
                 config
                 );


}


//add acount links to a list in the overview page
function loadAccountList(allAccounts) {
	
	accountLinks = [];
    allAccounts.map( stringAcc => {
        let acc = new Account(stringAcc);
        accountLinks.push([
            `/getAccount?plaidAccID=${acc.account_id}`,
             `${acc.name}`
            ]);
        //need an onclick event to change the cookie
    });
    console.log("accountLinks array: " + accountLinks);
    addAccountLinks(overviewURL, accountLinks);
	
}


//add acount links to a list in the overview page
async function addAccountLinks(accountsOverviewURL, accountLinks) {

    //"Accounts" section header links back to overview
    document.getElementById("accounts-overview-link").href = accountsOverviewURL;

    //Each "Account k" links to that account page
    let htmlList = document.getElementById("list-for-acc-links");
    accountLinks.forEach(accLink => {
        htmlList.innerHTML = htmlList.innerHTML +
         `<li class="acc-link-list"><a href="accountDetails.html"
          class="acc-link"
          onClick=accDetailsOnClick(${accLink[0]})> 
          ${accLink[1]}</a></li>`;
    });
}


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


//onlick function with parameters for sending user to different acc details pages
function accDetailsOnClick(newAcc) {
    document.cookie = `activeAccount=$newAcc`;
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