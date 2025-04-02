async function findAll() {
    const httpResp = await fetch('/api/dados');
    if (!httpResp.ok) {
        alert('Erro ao buscar dados');
        return;
    }
    const dados = await httpResp.json();
    const elDados = document.querySelector('#dados > tbody');
    elDados.innerHTML = '';
    dados.forEach(dado => {
        const item = `
            <tr>
                <td>${dado.id}</td>
                <td>${dado.nome}</td>
                <td><button onclick="findById(${dado.id})">Ver detalhes</button></td>
            </tr>`;
        elDados.insertAdjacentHTML('beforeend', item);
    });
}

async function findById(id) {
    if (!id) {
        alert('ID inválido');
        return;
    }
    const httpResp = await fetch(`/api/dados/${id}`);
    if (!httpResp.ok) {
        alert('Erro ao buscar dados');
        return;
    }
    const dados = await httpResp.json();
    const elDetalhes = document.getElementById('detalhes');
    elDetalhes.innerHTML = '';
    const detalhes = `
        <h2>#${dados.id} - ${dados.nome}</h2>
        <p>Data de nascimento: ${dados.dataNascimento}</p>
        <p>E-mail: ${dados.email}</p>
        <p>Telefone: ${dados.telefone}</p>`;
    elDetalhes.insertAdjacentHTML('beforeend', detalhes);
}
