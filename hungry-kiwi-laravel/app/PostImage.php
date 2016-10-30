<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PostImage extends Model
{
    protected $fillable = [
        'id', 'post_id', 'img',
    ];
    public static function getImage($post_id) {
        return static::whereIn('post_id', [$post_id]) -> get();
    }

    public static function getByPostId($post_id) {
        return static::where('post_id', $post_id) -> get(['img']);
    }
}
