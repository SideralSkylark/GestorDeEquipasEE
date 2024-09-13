document.addEventListener('DOMContentLoaded', function() {
    const listaFuncionarios = document.getElementById('lista-funcionarios');
    const listaAtividades = document.getElementById('lista-atividades');
    const nomeFuncionario = document.getElementById('nome-funcionario');
    const btnEditar = document.getElementById('btn-editar');
    const btnRemover = document.getElementById('btn-remover');
    let atividadeSelecionada = null;

    // Carregar atividades do funcionário selecionado
    listaFuncionarios.addEventListener('click', function(e) {
        if (e.target && e.target.nodeName === 'LI') {
            const funcionarioId = e.target.getAttribute('data-id');
            carregarAtividades(funcionarioId);
        }
    });

    function carregarAtividades(funcionarioId) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `carregarAtividades?funcionarioId=${funcionarioId}`, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                listaAtividades.innerHTML = xhr.responseText;
                nomeFuncionario.innerText = document.querySelector(`[data-id="${funcionarioId}"]`).innerText;

                // Desmarcar atividade selecionada
                atividadeSelecionada = null;
                btnEditar.disabled = true;
                btnRemover.disabled = true;
            }
        };

        xhr.send();
    }

    // Função para abrir tela de adicionar atividade
    window.abrirAdicionarAtividade = function() {

        window.location.href = 'adicionarAtividade.jsp';
    }

    // Função para abrir tela de editar atividade
    window.abrirEditarAtividade = function() {
        if (atividadeSelecionada) {
            window.location.href = `editarAtividade.jsp?id=${atividadeSelecionada}`;
        } else {
            alert("Nenhuma atividade selecionada para edição.");
        }
    }

    // Função para remover uma atividade
    window.removerAtividade = function() {
        if (atividadeSelecionada) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', `removerAtividade?id=${atividadeSelecionada}`, true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    carregarAtividades(document.querySelector('.funcionario-item.selected').getAttribute('data-id'));
                }
            };
            xhr.send();
        } else {
            alert("Nenhuma atividade selecionada para remoção.");
        }
    }

    // Marcar atividade selecionada
    listaAtividades.addEventListener('click', function(e) {
        if (e.target && e.target.nodeName === 'LI') {
            const atividadeId = e.target.getAttribute('data-id');
            atividadeSelecionada = atividadeId;
            btnEditar.disabled = false;
            btnRemover.disabled = false;
        }
    });
});
