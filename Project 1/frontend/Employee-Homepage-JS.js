window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Finance Manager') {
            window.location.href = 'Finance-Manager-HP.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'Expense-Reimbursement-HP.html';
    }
    
    populateTableWithReimbursements();
});

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }

})

async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
        
    });
    
    let tbodyElement = document.querySelector("#reimbursements-table tbody");
    tbodyElement.innerHTML = '';
    let reimbursementArray =  await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];
       
        let tr = document.createElement('tr');

        let td1 = document.createElement('td');
        td1.innerHTML = reimbursement.id;

        let td2 = document.createElement("td");
        td2.innerHTML = reimbursement.reimbursementAmount

        let td3 = document.createElement("td");
        td3.innerHTML = reimbursement.reimbursementSubmitted;

        let td4 = document.createElement("td");
        td4.innerHTML = reimbursement.reimbursementResolved;

        let td5 = document.createElement("td"); 
        
        let td6 = document.createElement("td");
        td6.innerHTML = reimbursement.type;

        let td7 = document.createElement('td');
        td7.innerHTML = reimbursement.reimbursementDesc;

        let td8 = document.createElement('td');
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Receipt Image';
        td8.appendChild(viewImageButton);

        let td9 = document.createElement('td');
        td9.innerHTML = reimbursement.authorId;

        let td10 = document.createElement('td');

        if (reimbursement.financeManagerId != 0) {
            td5.innerHTML = reimbursement.status;
            td10.innerHTML = reimbursement.financeManagerId;
        } else {
            td5.innerHTML = 'PENDING';
            td10.innerHTML = '-';
        }

        viewImageButton.addEventListener('click', () => {

            let reimbursementImageModal = document.querySelector('#reimbursement-image-modal');

            let modalCloseElement = reimbursementImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimbursementImageModal.classList.remove('is-active');
            });

            let modalContentElement = reimbursementImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimbursement/${reimbursement.id}/image`);
            modalContentElement.appendChild(imageElement);

            reimbursementImageModal.classList.add('is-active');
        });

        tr.appendChild(td1); 
        tr.appendChild(td2); 
        tr.appendChild(td3); 
        tr.appendChild(td4); 
        tr.appendChild(td5); 
        tr.appendChild(td6); 
        tr.appendChild(td7); 
        tr.appendChild(td8); 
        tr.appendChild(td9); 
        tr.appendChild(td10); 

        tbodyElement.appendChild(tr);
    }
}

let reimbursementSubmitButton = document.querySelector('#submit-reimbursement-btn');

reimbursementSubmitButton.addEventListener('click', submitReimbursement);

async function submitReimbursement() {
    
    let reimbursementAmount = document.querySelector('#reimbursement-amount');
    let reimbursementType = document.querySelector('#reimbursement-type');
    let reimbursementDescInput = document.querySelector('#reimbursement-description');
    let reimbursementImageInput = document.querySelector('#reimbursement-file');

    const file = reimbursementImageInput.files[0];

    let formData = new FormData();
    formData.append('reimburse_amount', reimbursementAmount.value);
    formData.append('reimburse_type', reimbursementType.value);
    formData.append('reimburse_decription', reimbursementDescInput.value);
    formData.append('reimburse_recipt', file);

    let res = await fetch('http://localhost:8080/reimbursements', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) {
        populateTableWithReimbursements(); 
    } else if (res.status === 400) { 
        let reimbursementForm = document.querySelector('#reimbursement-submit-form');

        let data = await res.json();

        let pTag = document.createElement('p');
        pTag.innerHTML = data.message;
        pTag.style.color = 'red';

        reimbursementForm.appendChild(pTag);
    }
}