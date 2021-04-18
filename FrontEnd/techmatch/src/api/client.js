import axiosBase from 'axios'
const axios = axiosBase.create({
    headers: {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
    },
    responseType: 'json'
});
export default axios