<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Recipe extends Model
{
    protected $fillable = [
        'id',
        'user_id',
        'name',
        'type',
        'is_vegi',
        'require_min',
        'comment_num',
        'rating',
        'content',
        'img',
        'is_img',
        'view',
        'created_at',
        'updated_at',

    ];

    public function comments() {
        return $this -> hasMany('App\RecipeComment');
    }
}
