

// API 서버와 통신이 필요한 모든 기능은 서버에서 데이터를 가져오는(fetch)시간을 고려해야 한다. 
// 흔히 ‘처리중..’ 혹은 ‘로딩중..’과 같은 메시지가 보이는 모달창을 통해 이를 처리한다. 
const FetchingModal = () => {
    return (
        <div
            className={`fixed top-0 left-0 z-[1055] flex h-full w-full place-items- center justify-center bg-black bg-opacity-20`}>
            <div
                className=" bg-white rounded-3xl opacity-100 min-w-min h-1/4 min-w- [600px] flex justify-center items-center ">

                <div className="text-4xl font-extrabold text-orange-400 m-20"> Loading.....
                </div>
            </div>
        </div>
    );

}
export default FetchingModal;