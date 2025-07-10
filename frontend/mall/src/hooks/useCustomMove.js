import { useCallback, useState } from "react";
import { createSearchParams, useNavigate, useSearchParams } from "react-router-dom";


const getNum = (param, defaultValue) => {
    if(!param){
        return defaultValue
    }
    return parseInt(param);
}

const useCustomMove = () => {
    const navigate = useNavigate();
    const [refresh, setRefresh] = useState(false);

    const [queryParams] = useSearchParams();

    const page = getNum(queryParams.get('page'), 1);
    const size = getNum(queryParams.get('size'), 10);

    const queryDefault = createSearchParams({page, size}).toString();

    const moveToList = useCallback((pageParam) => {
        let queryStr = "";

        if(pageParam){
            const pageNum = getNum(pageParam.page, 1)
            const sizeNum = getNum(pageParam.size, 10)

            queryStr = createSearchParams({page:pageNum, size:sizeNum}).toString();
        }else{
            queryStr = queryDefault;
        }
        navigate({
            pathname:`../list`,
            search:queryStr
        });

        // moveToList()가 호출될 때마다 이를 변경
        
        // useEffect() 의 의존성 설정 대상이 변경되어야만 유지 이펙트가 
        // 지정한 함수가 실행되기때문에 같은페이지 눌러도 리프레시(함수호출)
        // 되게 설정
        setRefresh(!refresh);
    }, [page, size, refresh])

    const moveToModify = useCallback((num) => {
        console.log(queryDefault);
        navigate({pathname:`../modify/${num}`, search: queryDefault});
    })

    const moveToRead = (num) => {
        console.log(queryDefault);
        navigate({
            pathname:`../read/${num}`,
            search: queryDefault
        })
    }

    return {moveToList, moveToModify, moveToRead, page, size, refresh};
}

export default useCustomMove;