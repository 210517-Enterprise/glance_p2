window.onload =() => {
  document.getElementById("link").addEventListener('click', generateToken);
}

let generateToken = (async function() {
  let response = await fetch("/api/linktoken/create", {
    method: 'POST'
  });
  let tempToken = await response.json();

  console.log(tempToken);

  const handler = Plaid.create({
  token: tempToken.linkToken,
  onSuccess: async(public_token, metadata) => {
    console.log("Public token " + public_token);
    let response = await fetch("/api/linktoken/exchange", {
      method: 'POST',
      headers: {
        "Content-Type" : "application/x-www-form-urlencoded;charset=UTF-8",
      },
      body: public_token,
    })
    let accessToken = await response.json();
    console.log("Access token " + accessToken);

    let url = "/addAccount?acessToken=" + accessToken;
    console.log(url);

    let response2 = await fetch(url)
    console.log(response2);
  },
  onLoad: () => {},
  onExit: (err, metadata) => {
    supportHandler.report({
      error: error,
      institution: metadata.institution,
      link_session_id: metadata.link_session_id,
      plaid_request_id: metadata.request_id,
      status: metadata.status,
    });
  },
  onEvent: (eventName, metadata) => {
    console.log(eventName, metadata);
  },
  receivedRedirectUri: null,
});

handler.open();

})
