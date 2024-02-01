const CLIENT = '/api/clients/current';
const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            account: [],
            data1: [],
            deleteAccount: -1,
            accountType: [],
        };
    },
    created() {
        this.loadData();
        this.formatBudget();
        this.loadData2();
    },
    methods: {
        loadData() {
            axios.get(CLIENT)
                .then(data => {
                    this.data = data.data;
                    this.account = data.data.accountDTOS;
                    console.log("account", this.account);
                    console.log("data", this.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        loadData2() {
            axios.get(CLIENT)
                .then(data => {
                    this.data1 = data.data.clientsLoansDTOS;
                    console.log("client loans", this.data1);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        formatBudget(balance) {
            if (balance !== undefined && balance !== null) {
                return balance.toLocaleString("en-US", {
                    style: "currency",
                    currency: "ARS",
                    minimumFractingDigits: 0,
                });
            }
        },
        logout() {
            axios.post("/api/logout")
                .then(response => {
                    console.log(response);
                    if (response.status == 200) {
                        window.location.href = "./login.html";
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        createAccount() {
            axios.post("/api/clients/current/accounts?accountType=" + this.accountType)
                .then(response => {
                    console.log(response);
                    this.loadData();
                    this.successMsg("Account created successfully!");
                })
                .catch(error => {
                    this.error = error.response.data;
                    console.log(this.error);
                    this.errorMsg("Error creating account");
                });
        },
        changeStatus() {
            axios.patch("/api/clients/current/accounts?number=" + this.deleteAccount)
                .then(response => {
                    console.log(response);
                    if (response.status == 200) {
                        this.loadData();
                        this.successMsg("Account status changed successfully!");
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.errorMsg("Error changing account status");
                });
        },
        successMsg(message) {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "success",
                title: "Success!",
                text: message,
            });
        },
        errorMsg(message) {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "error",
                title: "Oops...",
                text: message,
            });
        },
        cerrarSession() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                customClass: {
                    container: 'custom-swal-container',
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    cancelButton: 'custom-swal-cancel',
                    confirmButton: 'custom-swal-confirm',
                },
                title: "Are you sure?",
                text: "You are about to log out.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#8B0000",
                cancelButtonColor: "#717171",
                confirmButtonText: "Yes, I'm sure!",
            }).then((result) => {
                if (result.isConfirmed) {
                    this.logout();
                }
            });
        },
    },
}).mount('#app');