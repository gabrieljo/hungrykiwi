<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PostLike extends Model
{
    protected $fillable = [
        'user_id', 'post_id', 'updated_at', 'created_at',
    ];
}


