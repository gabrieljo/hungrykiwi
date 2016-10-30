<?php

namespace App\Http\Controllers;

use App\Post;
use App\PostLike;
use App\Trans\LikeTransformer;
use Illuminate\Http\Request;

use App\Http\Requests;

class PostLikeController extends ApiController
{

    protected $trans;
    protected $request;

    /**
     * PostLikeController constructor.
     * @param $trans
     * @param $request
     */
    public function __construct(LikeTransformer $trans, Request $request)
    {
        $this->trans = $trans;
        $this->request = $request;
    }


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($post_id)
    {

        $limit = $this -> request -> get('limit') ?: 10;
        $like = PostLike::where('post_id', $post_id) -> orderby('id', 'ASCD') -> paginate($limit);

        $next_url = $like -> nextPageUrl();
        $result =  $this -> trans -> collection($like -> all());
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
        $user_id = $this -> getUserId($this -> request);
        $post_id = $this -> request -> post_id;
        $like_record = PostLike::where('user_id', $user_id) ->where('post_id', $post_id) -> first();
        if(count($like_record) > 0) {
            Post::likeNum($post_id, false);
            $like_record -> delete();
            return $this -> successText('cancel_like');
        } else {
            Post::likeNum($post_id, true);
            PostLike::create(array_add($this->request->all(), 'user_id', $user_id));
            return $this -> successText('success');
        }


    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
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
    public function destroy($id)
    {
        //
    }
}
