<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/25/2016
 * Time: 9:12 PM
 */
namespace App\Trans;
class RecipeTransformer extends \App\Trans\BasicTransformer  {
    public function transform($data) {
        return [
            'id' => $data['id'],
            'type' => $data['type'],
            'is_vegi' => $data['is_vegi'],
            'name' => $data['name'],
            'require_min' => $data['require_min'],
            'rating' => $data['rating'],
            'img' => $data['img'],
            'view' => $data['view'],
        ];
    }
}