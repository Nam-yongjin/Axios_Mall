

// 모달 컴포넌트: 등록/수정/삭제 작업의 결과를 보여줌
const ResultModal = ({ title, content, callbackFn }) => {

    // 모달 창은 ‘Close Modal’을 누르거나 회색으로 보이는 전체 영역을 클릭해도 사라지게 된다.
    return (
        <div className={`fixed top-0 left-0 z-[1055] flex h-full w-full justify-center bg-black bg-opacity-20`}
            onClick={() => { if (callbackFn) { callbackFn() } }}>
            <div className="absolute bg-white shadow dark:bg-gray-700 opacity-100 w-1/4 rounded	mt-10 mb-10 px-6 min-w-[600px]">
                <div className="justify-center bg-warning-400 mt-6 mb-6 text-2xl border-b-4 border-gray-500"> {title}	</div>
                <div className="text-4xl	border-orange-400 border-b-4 pt-4 pb-4"> {content} </div>
                <div className="justify-end flex ">
                    <button className="rounded bg-blue-500 mt-4 mb-4 px-6 pt-4 pb-4 text-lg text-white" 
                    onClick={() => { if (callbackFn) { callbackFn() } }}>Close Modal</button>
                </div>

            </div>

        </div>
    );
}
export default ResultModal;