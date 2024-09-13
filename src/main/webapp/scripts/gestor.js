document.addEventListener('DOMContentLoaded', function() {
    const listaFuncionarios = document.getElementById('lista-funcionarios');
    const listaAtividades = document.getElementById('lista-atividades');
    const nomeFuncionario = document.getElementById('nome-funcionario');
    const btnEditar = document.getElementById('btn-editar');
    const btnRemover = document.getElementById('btn-remover');
    const modal = document.getElementById('modal-atividade');
    const closeModal = document.querySelector('.modal .close');
    const formAtividade = document.getElementById('form-atividade');
    const atividadeIdField = document.getElementById('atividade-id');
    const descricaoField = document.getElementById('descricao');
    const prioridadeField = document.getElementById('prioridade');
    const dataInicioField = document.getElementById('dataInicio');
    const dataTerminoField = document.getElementById('dataTermino');
    const funcionarioIdField = document.getElementById('funcionario-id');
    let atividadeSelecionada = null;
    let funcionarioSelecionado = null;

    // Carregar atividades do funcionário selecionado
    listaFuncionarios.addEventListener('click', function(e) {
        if (e.target && e.target.nodeName === 'LI') {
            const funcionarioId = e.target.getAttribute('data-id');
            funcionarioSelecionado = funcionarioId;
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

    // Função para abrir modal de adicionar atividade
    window.abrirAdicionarAtividade = function() {
        modal.style.display = 'block';
        document.getElementById('modal-titulo').innerText = 'Adicionar Atividade';
        formAtividade.reset();
        atividadeIdField.value = '';
        funcionarioIdField.value = funcionarioSelecionado;
    }

    // Função para abrir modal de editar atividade
    window.abrirEditarAtividade = function() {
        if (atividadeSelecionada != null) {
            modal.style.display = 'block';
            document.getElementById('modal-titulo').innerText = 'Editar Atividade';

            // Preencher os campos do modal com os dados da atividade selecionada
            const atividadeItem = document.querySelector(`[data-id="${atividadeSelecionada}"]`);

            if (atividadeItem) {
                descricaoField.value = atividadeItem.querySelector('.descricao').innerText;
                prioridadeField.value = atividadeItem.querySelector('.prioridade').innerText;
                dataInicioField.value = atividadeItem.getAttribute('data-inicio');
                dataTerminoField.value = atividadeItem.getAttribute('data-termino');
                atividadeIdField.value = atividadeSelecionada;
                funcionarioIdField.value = funcionarioSelecionado;
            } else {
                alert("Atividade selecionada não encontrada.");
            }
        } else {
            alert("Nenhuma atividade selecionada para edição.");
        }
    }

    // Função para remover uma atividade
    window.removerAtividade = function() {
        if (atividadeSelecionada) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', `removerAtividade`, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
                if (xhr.status === 200) {
                    carregarAtividades(funcionarioSelecionado);
                }
            };
            const params = `id=${atividadeSelecionada}`;
            xhr.send(params);
        } else {
            alert("Nenhuma atividade selecionada para remoção.");
        }
    }

    // Função para enviar dados para adicionar uma nova atividade
    function adicionarAtividade() {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'adicionarAtividade', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (xhr.status === 200) {
                modal.style.display = 'none';
                carregarAtividades(funcionarioSelecionado);
            } else {
                console.error("Erro na requisição:", xhr.status, xhr.responseText);
            }
        };
        const params = `descricao=${descricaoField.value}&prioridade=${prioridadeField.value}&dataInicio=${dataInicioField.value}&dataTermino=${dataTerminoField.value}&funcionarioId=${funcionarioIdField.value}`;
        xhr.send(params);
    }

    // Função para enviar dados para editar uma atividade existente
    function editarAtividade() {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'editarAtividade', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (xhr.status === 200) {
                modal.style.display = 'none';
                carregarAtividades(funcionarioSelecionado);
            } else {
                console.error("Erro na requisição:", xhr.status, xhr.responseText);
            }
        };
        const params = `id=${atividadeIdField.value}&descricao=${descricaoField.value}&prioridade=${prioridadeField.value}&dataInicio=${dataInicioField.value}&dataTermino=${dataTerminoField.value}&funcionarioId=${funcionarioIdField.value}`;
        xhr.send(params);
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

    // Fechar modal
    closeModal.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Submeter o formulário
    formAtividade.addEventListener('submit', function(e) {
        e.preventDefault();
        if (atividadeIdField.value) {
            editarAtividade();
        } else {
            adicionarAtividade();
        }
    });
});