    const { createApp } = Vue
    const app = createApp({
        data() {
            return {
                data: [],
                fechaYHora: [],
                selectedType: 'CREDIT',
                selectedColor: 'GOLD',
            }
        },
        created() {
            this.loadData()
        },
        methods: {
            loadData() {
                axios.get("/api/clients/current")
                    .then(data => {
                        this.data = data.data.cardDTOS
                        console.log(this.data)
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            createCard() {
                axios.post("/api/clients/current/cards?colors=" + this.selectedColor + "&type=" + this.selectedType)
                    .then(response => {
                        console.log(response.cardDTOS)
                    })
                    .catch(error => {
                        console.log(error.response.data)
                    })
            },
        }
    }).mount('#app')