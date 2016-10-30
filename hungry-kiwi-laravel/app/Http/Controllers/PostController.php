<?php

namespace App\Http\Controllers;

use App\PostComment;
use App\Trans\PostTransformer;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Util;
use App\Post;
use App\PostImage;
use App\User;

class PostController extends ApiController
{

    protected $trans;
    protected $request;

    /**
     * PostController constructor.
     * @param $trans
     * @param $request
     */
    public function __construct(PostTransformer $trans, Request $request)
    {
        $this->trans = $trans;
        $this->request = $request;
    }


    public function get(Post $post ) {
        return $post;
    }


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $limit = request() -> get('limit') ?: 5;
        $pages = Post::orderBy('id', 'desc') -> paginate($limit);
        $next_url = $pages -> nextPageUrl();
        $posts = $pages -> items();

        $data = $this -> trans -> collection($posts);

        return $this -> successArray([
            'next_page_url' => $next_url,
            'data' => $data,
        ]);

    }

    public static function imagePath() {
        return 'images/post/';
    }


    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(){
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


        $user_id = $this->getUserId($request);
        $data = array_add($request->except(['img']), 'user_id', $user_id);

        $post = Post::create($data); // save post data

        // save images
        $files = $request->file('img');
        if ($files != null) {
            foreach ($files as $file) {
                $path = Util::saveBigImage($file, Util::POST);
                $data = [
                    'post_id' => $post->id,
                    'img' => $path,
                ];
                // save to db
                PostImage::create($data);
            }
        }



        return $this -> successText("success");
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $post = Post::find($id);
        return $post;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {

        if($this -> cuAuthenticate($this -> request) == false) {
            $this -> forbidden();
        }
        $post = Post::find($id);
        $post -> update($this -> request -> except([]));
        return $this -> successText('success');
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id){

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id){
        if($this -> cuAuthenticate($this -> request) == false) {
            $this -> forbidden();
        }

        $post = Post::find($id);

        foreach($post -> comments as $comment) {
            $comment -> delete();
        }
        foreach($post -> likes as $like) {
            $like -> delete();
        }
        $post -> delete();
        return $this -> successText('success');
    }
}
