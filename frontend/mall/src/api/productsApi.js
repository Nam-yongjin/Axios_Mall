import axios from "axios";
import { API_SERVER_HOST } from "./todoApi";


const host = `${API_SERVER_HOST}/api/products`

export const postAdd = async (product) => {
    // postAdd()에서 주의할 점은 기본적으로 "Content-Type"을 "application/json"을 이용하기 때문에 
    // 파일 업로드를 같이 할 때는 "multipart/form-data" 헤더 설정을 추가해줘야 된다는 점이다. 
    const header = { headers: { "Content-Type": "multipart/form-data" } }

    // 경로 뒤 '/' 주의
    const res = await axios.post(`${host}/`, product, header);

    return res.data;
}

export const getList = async (pageParam) => {
    const { page, size } = pageParam;

    const res = await axios.get(`${host}/list`, { params: { page: page, size: size } });

    return res.data;
}

export const getOne = async (pno) => {
    const res = await axios.get(`${host}/${pno}`);

    return res.data;
}

// 수정 작업은 등록과 마찬가지로 첨부파일이 존재하기에 'multipart/form-data'헤더를 
// 설정해서 전송 처리해야 하고 삭제 작업은 해당 상품의 번호만을 전달해서 처리한다.
export const putOne = async (pno, product) => {
    const header = { 
        headers: { 
            "Content-Type": "multipart/form-data" 
        } 
    } 
    const res = await axios.put(`${host}/${pno}`, product, header);

    return res.data;
}
export const deleteOne = async (pno) => {
    const res = await axios.delete(`${host}/${pno}`);

    return res.data;
}
