const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            selectedAccount: "1",
            amount: "",
            accountDest: "",
            description: "",
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data = response.data.accountDTOS
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        createTransfer() {
            const Transaction = {
                "amount": this.amount,
                "originAccount": this.selectedAccount,
                "destinationAccount": this.accountDest,
                "description": this.description
            }
            axios.post("/api/transactions", Transaction)
                .then(response => {
                    this.data = response.data;
                    console.log(this.data);
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
                // Redirige a ./accounts.html solo si el usuario hace clic en "OK"
                window.location.href = "./accounts.html";
            });
        }
        
    },
}).mount('#app')