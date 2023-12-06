
const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            clients: [],
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
                    this.data = response
                })
                .catch(error => {
                    console.log(error)
                })
        },
        createClient() {
            axios.post("/clients",
                {
                    "name": this.name,
                    "lastname": this.lastname,
                    "email": this.email,
                })
                .then(response => {
                    this.data = response
                })
                .catch(error => {
                    console.log(error)
                })
        },
        deleteClient() {
            axios.delete("/clients/6")
                .then(response => {
                    this.data = response
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
}).mount('#app')