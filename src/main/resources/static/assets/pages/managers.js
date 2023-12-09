
const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            name: "",
            lastname: "",
            email: "",
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios("/clients")
                .then(response => {
                    this.data = response.data._embedded.clients
                })
                .catch(error => {
                    console.log(error)
                })
        },
        createClient(event) {
            event.preventDefault()
            axios.post("/clients",
                {
                    "name": this.name,
                    "lastname": this.lastname,
                    "email": this.email,
                })
                .then(response => {
                    this.data = response
                    this.loadData()
                    this.cleanInputs()
                })
                .catch(error => {
                    console.log(error)
                })
        },
        deleteClient(event) {
            event.preventDefault()
            axios.delete("/clients/1")
                .then(response => {
                    this.data = response
                    this.loadData()
                    this.cleanInputs()
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
}).mount('#app')