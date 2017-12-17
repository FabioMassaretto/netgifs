function logout() {
    $.ajax({
        url: "http://localhost:8083/netgifs/logout",
        type:"POST",
        success: function(data){
            window.location.href = "http://localhost:8083/netgifs/";
        }
    })
}