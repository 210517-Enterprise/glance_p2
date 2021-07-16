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
  console.log(accounts[0].balances);

  let validAccts = [];
  for (let i = 0; i < accounts.length; i++) {
    if(accounts[i].match(/depository/g) != null) {
      validAccts.push(accounts[i]);
    }
  }

  console.log(validAccts);

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
