// 비동기 함수 추가- 내부에 axios.get() 사용
async function get1(bno){

    const result = await axios.get(`/replies/list/${bno}`)

    // console.log(result.data)

    return result.data;
}

// 댓글 목록을 json으로 비동기로 가져오는 함수
async function getList({bno, page, size, goLast}){
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

// 댓글 등록 처리 함수 추가
async function addReply(replyObj){
    const response = await axios.post(`/replies/`, replyObj)
    return response.data
}

// 댓글 조회, 수정 코드 추가
async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

// 조회, 수정 json 데이터 처리 통신 코드 추가
async function modifyReply(replyObj){
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

// 댓글 삭제 함수 추가
async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}