window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Employee') {
            window.location.href = 'Employee-Homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'Expense-Reimbursement-HP.html';
    }

    populateTableWithReimbursements();

});

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        method: 'POST',
        credential: 'include'
    });

    if (res.status === 200) {
        window.location.href = 'Expense-Reimbursement-HP.html';
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
        td1.innerHTML = reimbursement.reimburseId;

        let td2 = document.createElement("td");
        td2.innerHTML = reimbursement.reimburseAmount;

        let td3 = document.createElement("td"); 
        td3.innerHTML = reimbursement.reimburseSubmitted;

        let td4 = document.createElement("td"); 
        td4.innerHTML = reimbursement.reimburseResolved;

        let td5 = document.createElement("td");
        
        let td6 = document.createElement("td");
        td6.innerHTML = reimbursement.reimburseType;

        let td7 = document.createElement('td');
        td7.innerHTML = reimbursement.reimburseDescription;

        let td8 = document.createElement('td');
        td8.innerHTML = reimbursement.employeeId;

        let td9 = document.createElement('td'); 

        let td10 = document.createElement('td');
        let td11 = document.createElement('td');

        let td12 = document.createElement('td'); 
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Receipt Image';
        td12.appendChild(viewImageButton);


        if (reimbursement.financeManagerId != 0) {
            td5.innerHTML = reimbursement.status;
            td9.innerHTML = reimbursement.financeManagerId;
        } else {
            td5.innerHTML = 'PENDING';
            td9.innerHTML = '-';
        
            let statusInput = document.createElement('input');
            statusInput.setAttribute('type',String);

            let statusButton = document.createElement('button');
            statusButton.innerText = 'Add Reimbursement Status';
            statusButton.addEventListener('click', async () => {
                let res = await fetch(`http://localhost:8080/reimbursements/${reimbursement.id}/status`, 
                    {
                        credentials: 'include',
                        method: 'PATCH',
                        body: JSON.stringify({
                            status: statusInput.value
                        })
                    });

                if (res.status === 200) {
                    populateTableWithReimbursements();
                }
            });

            td10.appendChild(statusButton);
            td11.appendChild(statusInput);
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
        })

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
        tr.appendChild(td11);
        tr.appendChild(td12);
        tbodyElement.appendChild(tr);
    }
}