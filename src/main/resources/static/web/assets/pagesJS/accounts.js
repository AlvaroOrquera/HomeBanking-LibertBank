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
        logout(){
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./login.html"
                    }
                })
        },
    }
}).mount('#app')