<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PostComment extends Model {
    protected $fillable = [
        'id', 'comment', 'post_id', 'user_id', 'created_at', 'updated_at'
    ];
}
