<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class RecipeComment extends Model
{
    protected $fillable = [
        'id',
        'user_id',
        'recipe_id',
        'rate',
        'comment',
        'created_at',
        'updated_at',
    ];
}
