let user = null;
let token = null;
let accounts = [];

window.onload = function() {
  getAccounts();
}

let getAccounts = async function() {
  let response = await fetch("/getAccounts");
  accounts = await response.json();
  console.log(accounts);
  console.log(accounts[0]);
  console.log(accounts[0].balances)
}
