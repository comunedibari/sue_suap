// Firma remota

    function SignContent_nextAction(content) {
  
    //alert("SignContent_nextAction Override called");
    doRemoteSign();
  }
  
  var remotesign_pathPrefix = "";
  
  function init_pathPrefix(pathPrefix) {
    remotesign_pathPrefix = pathPrefix;
    //alert('remoteSign_pathPrefix='+remotesign_pathPrefix);
  }
  
  function loadRemoteSignApplet() { 
          
          var unsignedDataField = "Content";
          var signedDataField = "signedData";
          var usernameField = "username";
          
          var remoteSignMode = "remoteSignMode";
          var trustoreFilename = "root_ca_trustore.jks";
          var trustorePassword = "people_demo";
          var formName = "signForm";
          var posX = screen.width/2-125;
          var posY = screen.height/2-50;
          var newwin = window.open(remotesign_pathPrefix + "/popupApplet.jsp?IRemoteSignWSUsernameInputField=" + usernameField +
                      "&IRemoteSignWSSignModeInputField=" + remoteSignMode + 
                      "&IRemoteSignWSUnsignedDataInputField=" + unsignedDataField +
                      "&IRemoteSignWSSignedDataOutputFormName=" + formName +
                      "&IRemoteSignWSSignedDataOutputField=" + signedDataField + 
                      "&TrustoreFilename=" + trustoreFilename + 
                      "&TrustorePassword=" + trustorePassword,"appletPopup","height=110,width=210,left="+posX+",top="+posY+",titlebar=0,toolbar=0,status=0,scrollbars=0,resizable=0,menubar=0");
          newwin.focus();
        }

  
function doRemoteSign() {
  //var response = confirm("L'operazione di firma digitale con la Smartcard non si e' conclusa correttamente. \nPremi OK per riprovare oppure 'Cancel' per attivare il processo di firma remota");
  //var response = confirm("Premi OK per attivare il processo di firma remota");
  //if(response == true) { 
    //document.getElementById("issueText").value="hai scelto di riprovare";
    loadRemoteSignApplet();
  //} else { 
    //var pin = prompt("Inserire il PIN per l'attivazione dell'operazione di firma remota", "");

    //var signedData = document.getElementById("signedData");
    //  var content = document.getElementById("Content").innerHTML;
    //signedData.value = "REMOTESIGN:PIN:"+pin+":CONTENT:" + content;
    //document.getElementById("signForm").submit();
  //}
}

