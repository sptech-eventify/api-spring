// Capturar o valor do parâmetro hash
const urlParams = new URLSearchParams(window.location.search);
const hash = urlParams.get('hash');
hashSpan.innerHTML = hash;
var cont = 0;


// Função para copiar o valor de um span
function copiarParaClipboard(texto) {
    const elementoTemporario = document.createElement('textarea');
    elementoTemporario.value = texto;
  
    document.body.appendChild(elementoTemporario);
    elementoTemporario.select();
  
    document.execCommand('copy');
    document.body.removeChild(elementoTemporario);
  }

// Função para redirecionar após o carregamento do iframe
document.getElementById('form').addListener ("load", function () {
    console.log('iframe carregado '+ cont);
    if(cont == 0){
        cont++;
    }else{
        window.location.href = 'https://www.example.com?hash=' + hash;
    }
}); 
