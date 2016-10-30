<?php

namespace App\Http\Controllers;

use App\Recipe;
use App\RecipeComment;
use App\Trans\RateCommentTransformer;
use Illuminate\Http\Request;

use App\Http\Requests;

class RecipeCommentController extends ApiController 
{
    protected $request;
    protected $trans;

    /**
     * RecipeCommentController constructor.
     * @param $request
     * @param $trans
     */
    public function __construct(Request $request, RateCommentTransformer $trans)
    {
        $this->request = $request;
        $this->trans = $trans;
    }


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($recipe_id) {

        $limit = request() -> get('limit') ?: 10;
        $comments = RecipeComment::where('recipe_id', $recipe_id) -> orderby('id', 'ASCD') -> paginate($limit);
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
    public function store(Request $request, $recipe_id){
        $user_id = $this -> getUserId($request);
        $comment = array_add($this -> request -> all(), 'user_id', $user_id);
        $comment = array_add($comment, 'recipe_id', $recipe_id);

        RecipeComment::create($comment);
        $recipe = Recipe::find($recipe_id);
        $comment_num = $recipe -> comment_num;
        $recipe -> update([
            'comment_num' => $comment_num +1,
        ]);
        return $this -> successText('success');


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
