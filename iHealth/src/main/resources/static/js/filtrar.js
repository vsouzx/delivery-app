document.addEventListener('DOMContentLoaded', function () {
	
	var campoFiltro = document.querySelector("#filtrar-tabela");
	
	
   	campoFiltro.addEventListener("input", function(){
	
    var cursos = document.querySelectorAll(".curso");
	
	
    if(this.value.length > 0){
	
		
		
        for(var i = 0; i < cursos.length; i++){
            var curso = cursos[i];
            
            var tdNome = curso.querySelector(".info-nome");     
            
            var nome = tdNome.textContent;
            
           
            var expressao = new RegExp(this.value, "i");
            if(!expressao.test(nome)){
                curso.classList.add("invisivel");
            }else{
                curso.classList.remove("invisivel");
            }
        }
    }else{
        for(var i = 0; i < cursos.length; i++){
            var curso = cursos[i];
            curso.classList.remove("invisivel");
        }
    }
    
})
});
