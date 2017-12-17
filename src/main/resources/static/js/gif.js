function visualizarGif(gif){
    $("#show-gif").html(`
        <div>Nome: ${gif.title}</div>
        <div>Descrição: ${gif.alt}</div>
        <img class="gif-height" src="${gif.src}"/>
    `);
    document.getElementById("myNav").style.width = "100%";
}

function closeNav() {
    document.getElementById("myNav").style.width = "0%";
}