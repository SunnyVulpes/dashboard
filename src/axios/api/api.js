import http from "../index.js"

export async function getTempList() {
    try {
        const res = await http.get("/api/temperature/getlist")
        console.log(res.data.data)
        return res.data.data
        
    }catch (err) {
        console.log(err)
        throw err
    }

}

export async function getCurTemp() {
    try {
        const res = await http.get("/api/temperature/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function getHumidityList() {
    try {
        const res = await http.get("/api/shidu/getlist")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function getCurHumidity() {
    try {
        const res = await http.get("/api/shidu/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function countPeople() {
    try {
        const res = await http.get("/api/people/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        return err
    }
}

export async function getPhotoUrl() {
    try {
        const res = await http.get("/api/photo/get", {
            responseType: "blob"
        })
        return URL.createObjectURL(res.data)
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function getPhotoUrl_4() {
    try {
        const res = await http.get("/api/photo/geturl")
        return res.data
    }catch (err) {
        console.log(err)
        throw err
    }
}