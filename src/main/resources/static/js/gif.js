function visualizarGif(gif){
    $("#show-gif").html(`
        <form action="/netgifs/user/favorite" method="POST">
        
            <input type="hidden" name="name" value="${gif.title}">
            <input type="hidden" name="url" value="${gif.src}">
            <input type="hidden" name="description" value="${gif.alt}">
            
            <div>Nome: ${gif.title}</div>
            <div>Descrição: ${gif.alt}</div>
            <img class="gif-height" src="${gif.src}"/>
            
            <div>
                <button class="cor" type="submit">Adicionar aos Favoritos</button>
            </div>
        </form>
    `);
    document.getElementById("myNav").style.width = "100%";
}

function visualizarGifFavorito(gif){
    $("#show-gif").html(`
        <form action="/netgifs/user/delete/favorite" method="POST">
        
            <input type="hidden" name="name" value="${gif.title}">
            
            <div>Nome: ${gif.title}</div>
            <div>Descrição: ${gif.alt}</div>
            <img class="gif-height" src="${gif.src}"/>
            
            <div>
                <button class="cor" type="submit">Remover dos Favoritos</button>
            </div>
            
        </form>
    `);
    document.getElementById("myNav").style.width = "100%";
}


function closeNav() {
    document.getElementById("myNav").style.width = "0%";
}