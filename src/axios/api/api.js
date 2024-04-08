import http from "../index.js"

export async function getTempList() {
    try {
        const res = await http.get("/temperature/list")
        console.log(res.data)
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }

}

export async function getCurTemp() {
    try {
        const res = await http.get("/temperature/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function getHumidityList() {
    try {
        const res = await http.get("/shidu/getlist")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function getCurHumidity() {
    try {
        const res = await http.get("/shidu/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        throw err
    }
}

export async function countPeople() {
    try {
        const res = await http.get("people/get")
        return res.data.data
    }catch (err) {
        console.log(err)
        return err
    }
}

export async function getPhotoUrl() {
    try {
        const res = await http.get("photo/get", {
            responseType: "blob"
        })
        return URL.createObjectURL(res.data)
    }catch (err) {
        console.log(err)
        throw err
    }
}