<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Post extends Model
{
    protected $fillable = [
    	'id', 'user_id', 'restr_id', 'content', 'like_num', 'comment_num', 'is_img', 'created_at', 'updated_at'
    ];

    public function image() {
        return $this -> hasMany('App\PostImage');
    }


    public static function likeNum($post_id, $like) {

        $post = Post::find($post_id);

        $value =$post -> like_num;
        if($like == true) {
            $value += 1;
        } else {
            $value -= 1;
        }

        $post -> update([
            'like_num' => $value,
        ]);
    }

    public static function commentNum($post_id, $comment) {
        $post = Post::find($post_id);
        $value =$post -> comment_num;
        if($comment == true) {
            $value += 1;
        } else {
            $value -= 1;
        }
        $post -> update([
           'comment_num' => $value,
        ]);

    }
    public function comments() {
        return $this -> hasMany('App\PostComment');
    }

    public function likes() {
        return $this -> hasMany('App\PostLike');
    }

}
