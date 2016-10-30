<?php

namespace App\Http\Controllers;

use App\Post;
use App\PostComment;
use App\Trans\CommentTransformer;
use Illuminate\Http\Request;

use App\Http\Requests;

class PostCommentController extends ApiController
{
    protected $trans;
    protected $request;

    /**
     * PostCommentController constructor.
     * @param $trans
     * @param $request
     */
    public function __construct(CommentTransformer $trans, Request $request)
    {
        $this->trans = $trans;
        $this->request = $request;
    }


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($post_id) {

        $limit = request() -> get('limit') ?: 10;
        $comments = PostComment::where('post_id', $post_id) -> orderby('id', 'ASCD') -> paginate($limit);
        $next_url = $comments -> nextPageUrl();
        $result = $this -> trans -> collection($comments -> all());
        return $this -> successArray([
            'data' => $result,
            'next_page_url' => $next_url,
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

        $user_id = $this -> getUserId($request);
        PostComment::create(array_add($request -> all(), 'user_id', $user_id));
        Post::commentNum($this -> request -> get('post_id'), true);
        return $this -> successText('success');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($post_id, $comment_id)
    {
        $content = $this -> request -> get('comment');
        $comment = PostComment::find($comment_id);

        $comment -> update([
            'comment' => $content,
        ]);

        return $this -> successText('success');
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($post_id, $comment_id){
        $post = Post::find($post_id);
        $curr_comment_num =  $post -> comment_num;
        PostComment::find($comment_id) -> delete();

        $post -> update([
             'comment_num' => $curr_comment_num - 1,
        ]);
        return $this -> successText('success');
    }
}
