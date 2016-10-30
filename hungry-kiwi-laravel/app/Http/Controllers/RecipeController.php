<?php

namespace App\Http\Controllers;

use App\Recipe;
use App\Trans\RecipeTransformer;
use App\User;
use Illuminate\Http\Request;

use App\Http\Requests;

class RecipeController extends ApiController
{
    protected $request;
    protected $trans;

    /**
     * RecipeController constructor.
     * @param $request
     * @param $trans
     */
    public function __construct(Request $request, RecipeTransformer $trans)
    {
        $this->request = $request;
        $this->trans = $trans;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index() {
        $min = $this -> request -> get('require_min');
        $is_vegi = $this -> request -> get('is_vegi');

        $builder = null;

        if($this -> request -> has('criterial')) {
            $criterial = $this -> request -> get('criterial');
            if($criterial == 0) $builder = Recipe::orderby('rating', 'DESC');
            else if($criterial == 1) $builder = Recipe::orderby('view', 'DESC');
        } else {
            $builder = Recipe::orderby('id', 'DESC');
        }
        if($min != 0) $builder = $builder -> where('require_min', '<', $min);
        if($is_vegi === 'true') $builder = $builder -> where('is_vegi', '=', 1);

        $limit = $this -> request -> get('limit') ?: 10;;
        $data = $builder -> paginate($limit);

        $trimed = $this -> trans -> collection($data -> items());
        return [
            'next_page_url' => $data -> nextPageUrl(),
            'data' => $trimed,
        ];
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
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $recipe =  Recipe::find($id);
        $recipe -> update([
                'view' => ($recipe -> view)+1,
            ]
        );
        $user = User::find($recipe -> user_id);

        return $this -> successArray([
            'data' => $recipe,
            'user' => [
                'id' => $user -> id,
                'first_name' => $user -> first_name,
                'last_name' => $user -> last_name,

            ]
        ]);
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
