const CLIENT = '/api/clients/current'
const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            account: [],
            data1: [],
        }
    },
    created() {
        this.loadData()
        this.formatBudget()
        this.loadData2()
        console.log("holitas")
    },
    methods: {
        loadData() {
            axios.get(CLIENT)
                .then(data => {
                    this.data = data.data
                    this.account = data.data.accountDTOS
                    console.log(this.account)
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        loadData2() {
            axios.get(CLIENT)
                .then(data => {
                    this.data1 = data.data.clientsLoansDTOS
                    console.log(this.data1)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        formatBudget(balance) {
            if (balance !== undefined && balance !== null) {
                return balance.toLocaleString("en-US", {
                    style: "currency",
                    currency: "ARS",
                    minimumFractingDigits: 0,
                })
            }
        },
        logout() {
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./login.html"
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
        createAccount() {
            axios.post("/api/clients/current/accounts")
                .then(response => {
                    console.log(response)
                    this.loadData()
                })
                .catch(error => {
                    this.error = error.response.data
                    console.log(this.error)
                })
        },
        cerrarSession() {
            Swal.fire({
                background: "linear-gradient(to right, #191970, #00BFFF) no-repeat 0 0 / cover",
                color: "white",
                title: "Are you sure?",
                text: "you are about to log out",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, I'm sure!",
            }).then((result) => {
                if (result.isConfirmed) {
                this.logout();
                }
            });
        }
    }
}).mount('#app')