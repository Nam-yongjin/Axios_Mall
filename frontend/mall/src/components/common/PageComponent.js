

// 페이징의 경우 모든 목록 관련 기능에서 사용해야 하므로 공통의 컴포넌트로 제작해서 사용
const PageComponents = ({ serverData, movePage }) => {

    return (
        <div className="m-6 flex justify-center">
            {serverData.prev ?
                <div className="m-2 p-2 w-16 text-center font-bold text-blue-400" onClick={() => movePage({ page: serverData.prevPage })}>
                    Prev </div> : <></>}

            {serverData.pageNumList.map(pageNum =>
                <div key={pageNum}
                    className={`m-2 p-2 w-12	text-center rounded shadow-md text-white
                        ${serverData.current === pageNum ? 'bg-gray-500' : 'bg-blue-400'}`}
                    onClick={() => movePage({ page: pageNum })}>
                    {pageNum}
                </div>
            )}

            {serverData.next ?
                <div className="m-2 p-2 w-16 text-center font-bold text-blue-400" onClick={() => movePage({ page: serverData.nextPage })}>
                    Next
                </div> : <></>}
        </div>

    );
}

export default PageComponents;