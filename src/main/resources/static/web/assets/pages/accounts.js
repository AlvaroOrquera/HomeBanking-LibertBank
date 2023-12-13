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
    },
    methods: {
        loadData() {
            axios.get("/api/clients/1")
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
            axios.get("/api/clients/1")
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
    }
}).mount('#app')