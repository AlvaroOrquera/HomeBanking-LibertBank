const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            selectedAccount: "",
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
            axios.post("/api/transactions?amount=" + this.amount + "&originAccount=" + this.selectedAccount + "&destinationAccount=" + this.accountDest + "&description=" + this.description)
                .then(response => {
                    this.data = response.data
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
    }
}).mount('#app')