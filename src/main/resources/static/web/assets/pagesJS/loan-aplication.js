const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            selectLoan: "1",
            amount: "",
            accountDest: "",
            payments: "1",
            paymentsFilter: "1",

        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/loans")
                .then(response => {
                    this.data = response.data
                    this.payments = response.data
                    console.log("hola", this.payments)

                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        Payments() {
            this.paymentsFilter = this.data.filter(loan => { return this.selectLoan == loan.id })[0]
        },
        createLoans() {
            const body = {
                "id": this.selectLoan,
                "amount": this.amount,
                "payments": this.payments,
                "destinationAccount": this.accountDest
            }
            axios.post("/api/loans", body)
                .then(response => {
                    this.data = response.data
                    console.log(this.data)
                    if (response.status === 201) {
                        this.successMsg();
                        this.statusTransaction = true;
                    } else {
                        this.errorMsg();
                        this.statusTransaction = false;
                    }
                })
                .catch(error => console.log(error));
        },
        
        errorMsg() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                color: "white",
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
            });
        },
        
        successMsg() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                color: "white",
                icon: "success",
                title: "Success!",
                text: "Transaction created successfully!",
            }).then(() => {
                // Utiliza el enrutador de Vue.js para redirigir a ./accounts.html
                window.location.href = "./accounts.html";
            });
        }
    }
}).mount('#app')