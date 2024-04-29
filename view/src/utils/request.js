import axios from 'axios'
import router from "@/router";

const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,
    timeout: 30000,
});
// 请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = JSON.parse(localStorage.getItem("pilot") || '{}')
    config.headers['token'] = user.token
    return config;
    },
    error => {
        console.log(error);
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code === '401') {
            router.push("/login")
        }
        return res;
    },
    error => {
        console.error(error);
        return Promise.reject(error);
    }
)
export default request;