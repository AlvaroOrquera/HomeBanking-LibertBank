
const { createApp } = Vue
const app = createApp({
    data() {
        return {
            name: "",
            maxAmount: "",
            paymentArray: "",
            percentage: ""
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/loans")
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
        },
        createLoan() {
            const paymentArray = this.paymentArray.split(',').map(Number);
            const body = {
                "name": this.name,
                "maxAmount": this.maxAmount,
                "payments": paymentArray,
                "percentage": this.percentage
            }
            axios.post("/api/admin/loans", body)
                .then(response => {
                    console.log(response)
                })
                .catch(error => {
                    console.log(error)
                })
        },
    }
}).mount('#app')