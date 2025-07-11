import { useRef, useState } from "react";
import { postAdd } from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = { pname: '', pdesc: '', price: 0, files: [] };

const AddComponent = () => {
    const [product, setProduct] = useState({ ...initState });

    // useRef()는 기존의 자바스크립에서 document.getElementById()와 유사한 역할을 한다. 
    // 리액트의 컴포넌트는 태그의 id 속성을 활용하면 나중에 동일한 컴포넌트를 여러 번 사용해서 
    // 화면에 문제가 생기기 때문에 useRef()를 이용해서 처리한다. 
    // 예제에서는 ADD 버튼을 클릭했을 때 첨부 파일의 선택된 정보를 읽어내서 첨부 파일의 정보를 파악하고 
    // 이를 Ajax 전송에 사용하는 FormData 객체를 구성해야 한다. FormData 타입으로 처리된 모든 내용은 
    // Ajax를 이용해서 서버를  호출할 때 사용하게 된다. 
    const uploadRef = useRef();

    const [fetching, setFetching] = useState(false);

    const [result, setResult] = useState(null);

    const {moveToList} = useCustomMove();

    const handleChangeProduct = (e) => {
        product[e.target.name] = e.target.value;
        setProduct({ ...product });
    }
    const handleClickAdd = (e) => {
        const files = uploadRef.current.files;

        const formData = new FormData();

        for(let i=0; i<files.length; i++){
            formData.append("files", files[i]);
        }
        formData.append("pname", product.pname)
        formData.append("pdesc", product.pdesc)
        formData.append("price", product.price)

        setFetching(true);
        postAdd(formData).then(data => {    // POST /api/products/ 를 호출
            setFetching(false);
            setResult(data.result); // db에서 생성된 pno 들어옴
        })
    }

    // ResultModal 종료
    const closeModal = () => {
        setResult(null);
        moveToList({page:1});
    }

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-4">
            {fetching ? <FetchingModal /> : <></>}
            {result ? <ResultModal title={'Product Add Result'} content={`${result}번 등록 완료`} callbackFn={closeModal}/> : <></>}

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Product Name</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md" name="pname" type={'text'} value={product.pname} onChange={handleChangeProduct} >
                    </input>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Desc</div>
                    <textarea
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y" name="pdesc" rows="4" onChange={handleChangeProduct} value={product.pdesc}>
                        {product.pdesc}
                    </textarea>
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Price</div>
                    <input
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                        name="price" type={'number'} value={product.price} onChange={handleChangeProduct}>
                    </input>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Files</div>
                    <input
                        ref={uploadRef}
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md" type={'file'} multiple={true}>
                    </input>
                </div>
            </div>
            <div className="flex justify-end">
                <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
                    <button type="button"
                        className="rounded p-4 w-36 bg-blue-500 text-xl	text-white "
                        onClick={handleClickAdd} >
                        ADD
                    </button>
                </div>
            </div>

        </div>

    );
}
export default AddComponent;