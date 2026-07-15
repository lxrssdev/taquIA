function changeQty(productId, delta){
    const action = delta > 0 ? 'increase' : 'decrease';
    fetch('/cart/items/' + productId + '/' + action, { method: 'POST' })
        .then(() => window.location.reload())
        .catch(err => console.error('No se pudo actualizar la cantidad', err));
}

function updateObservation(productId, observationText){
    const body = new URLSearchParams();
    body.append('observation', observationText);

    fetch('/cart/items/' + productId + '/update-observations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: body
    })
        .catch(err => console.error('No se pudo guardar la observación', err));
}

function removeItem(productId){
    fetch('/cart/items/' + productId, { method: 'DELETE' })
        .then(() => window.location.reload())
        .catch(err => console.error('No se pudo quitar el producto', err));
}

function seguirPidiendo(){
    window.location.href = '/menu';
}

function confirmarPedido(){
    window.location.href = '/order/confirm';
}