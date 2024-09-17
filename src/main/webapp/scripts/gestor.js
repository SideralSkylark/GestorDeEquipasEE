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

    // Função para carregar atividades
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

                // Adicionar evento de clique para cada atividade carregada
                document.querySelectorAll('.atividade-item').forEach(item => {
                    item.addEventListener('click', function() {
                        atividadeSelecionada = item.getAttribute('data-id');
                        btnEditar.disabled = false;
                        btnRemover.disabled = false;
                    });
                });
            }
        };
        xhr.send();
    }

    // Abrir o modal para adicionar atividade
    document.getElementById('btn-adicionar').addEventListener('click', function() {
        abrirAdicionarAtividade();
    });

    // Abrir o modal para editar atividade
    document.getElementById('btn-editar').addEventListener('click', function() {
        if (atividadeSelecionada) {
            abrirEditarAtividade(atividadeSelecionada);
        }
    });

    // Função para abrir o modal de adicionar atividade
    function abrirAdicionarAtividade() {
        limparFormulario();
        modal.style.display = 'block';
        document.getElementById('modal-titulo').innerText = 'Adicionar Atividade';
    }

    // Função para abrir o modal de editar atividade
    function abrirEditarAtividade(atividadeId) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `obterAtividade?id=${atividadeId}`, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const atividade = JSON.parse(xhr.responseText);
                atividadeIdField.value = atividade.id;
                descricaoField.value = atividade.descricao;
                prioridadeField.value = atividade.prioridade;
                dataInicioField.value = atividade.dataInicio;
                dataTerminoField.value = atividade.dataTermino;
                funcionarioIdField.value = atividade.funcionarioId;
                modal.style.display = 'block';
                document.getElementById('modal-titulo').innerText = 'Editar Atividade';
            }
        };
        xhr.send();
    }

    // Fechar o modal
    closeModal.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Enviar o formulário de adicionar/editar atividade
    formAtividade.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(formAtividade);
        const xhr = new XMLHttpRequest();
        const url = atividadeIdField.value ? 'editarAtividade' : 'adicionarAtividade';
        xhr.open('POST', url, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                modal.style.display = 'none';
                carregarAtividades(funcionarioSelecionado);
            }
        };
        xhr.send(formData);
    });

    // Função para limpar o formulário do modal
    function limparFormulario() {
        atividadeIdField.value = '';
        descricaoField.value = '';
        prioridadeField.value = 'ALTA';
        dataInicioField.value = '';
        dataTerminoField.value = '';
        funcionarioIdField.value = funcionarioSelecionado;
    }

    // Remover atividade selecionada
    document.getElementById('btn-remover').addEventListener('click', function() {
        if (atividadeSelecionada) {
            if (confirm('Tem certeza que deseja remover esta atividade?')) {
                const xhr = new XMLHttpRequest();
                xhr.open('POST', `removerAtividade?id=${atividadeSelecionada}`, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        carregarAtividades(funcionarioSelecionado);
                    }
                };
                xhr.send();
            }
        }
    });
});
